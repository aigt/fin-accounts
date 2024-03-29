package aigt.finaccounts.app.kafka.consumer

import aigt.finaccounts.app.kafka.AppKafkaConfig
import aigt.finaccounts.app.kafka.consumer.strategy.ConsumerStrategy
import aigt.finaccounts.app.kafka.createKafkaConsumer
import aigt.finaccounts.app.kafka.createKafkaProducer
import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.request.RequestStartTime
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.WakeupException
import java.time.Duration
import java.util.*

private val log = KotlinLogging.logger {}


class AppKafkaConsumer(
    private val config: AppKafkaConfig,
    private val consumerStrategies: List<ConsumerStrategy>,
    private val processor: AccountProcessor = AccountProcessor(),
    private val consumer: Consumer<String, String> = config.createKafkaConsumer(),
    private val producer: Producer<String, String> = config.createKafkaProducer(),
) {
    private val process = atomic(true)
    private val topicsAndStrategyByInputTopic = consumerStrategies.associate {
        val topics = it.topics(config)
        topics.input to TopicsAndStrategy(topics.input, topics.output, it)
    }

    fun run() = runBlocking {
        try {
            consumer.subscribe(topicsAndStrategyByInputTopic.keys)
            while (process.value) {

                val records: ConsumerRecords<String, String> =
                    withContext(Dispatchers.IO) {
                        consumer.poll(Duration.ofSeconds(1))
                    }

                if (!records.isEmpty)
                    log.info { "Receive ${records.count()} messages" }

                records.forEach { record: ConsumerRecord<String, String> ->
                    processRecord(record)
                }

            }
        } catch (ex: WakeupException) {
            // ignore for shutdown
        } catch (ex: RuntimeException) {
            // exception handling
            withContext(NonCancellable) {
                throw ex
            }
        } finally {
            withContext(NonCancellable) {
                consumer.close()
            }
        }
    }

    private suspend fun processRecord(
        record: ConsumerRecord<String, String>,
    ) {
        try {

            val ctx = FinAccountsContext(
                requestStartTime = RequestStartTime(startTime = Clock.System.now()),
            )

            log.info { "process ${record.key()} from ${record.topic()}:\n${record.value()}" }
            val (_, outputTopic, strategy) = topicsAndStrategyByInputTopic[record.topic()]
                ?: throw RuntimeException("Receive message from unknown topic ${record.topic()}")

            strategy.deserialize(record.value(), ctx)
            processor.exec(ctx)
            sendResponse(ctx, strategy, outputTopic)

        } catch (ex: Exception) {
            log.error(ex) { "error" }
        }
    }

    private fun sendResponse(
        context: FinAccountsContext,
        strategy: ConsumerStrategy,
        outputTopic: String,
    ) {
        val json = strategy.serialize(context)
        val resRecord = ProducerRecord(
            outputTopic,
            UUID.randomUUID().toString(),
            json,
        )
        log.info { "sending ${resRecord.key()} to $outputTopic:\n$json" }
        producer.send(resRecord)
    }

    fun stop() {
        process.value = false
    }

    private data class TopicsAndStrategy(
        val inputTopic: String,
        val outputTopic: String,
        val strategy: ConsumerStrategy,
    )
}
