package aigt.finaccounts.app.kafka

import aigt.finaccounts.api.v1.kmp.models.AccountCreateResponse
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestSerialize
import aigt.finaccounts.api.v1.kmp.responses.apiV1ResponseDeserialize
import aigt.finaccounts.app.kafka.consumer.AppKafkaConsumer
import aigt.finaccounts.app.kafka.consumer.strategy.ConsumerStrategyV1Jvm
import aigt.finaccounts.app.kafka.fixture.getAccountCreateRequest
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals


class KafkaControllerTest {

    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(
            OffsetResetStrategy.EARLIEST,
        )

        val producer = MockProducer<String, String>(
            true,
            StringSerializer(),
            StringSerializer(),
        )

        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1Jvm
        val outputTopic = config.kafkaTopicOutV1Jvm

        val app = AppKafkaConsumer(
            config = config,
            consumerStrategies = listOf(ConsumerStrategyV1Jvm()),
            consumer = consumer,
            producer = producer,
        )

        consumer.schedulePollTask {
            consumer.rebalance(
                Collections.singletonList(
                    TopicPartition(inputTopic, 0),
                ),
            )
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(getAccountCreateRequest()),
                ),
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result =
            apiV1ResponseDeserialize<AccountCreateResponse>(message.value())

        assertEquals(
            outputTopic,
            message.topic(),
        )
        assertEquals(
            "75038a32-9d63-4394-968b-d33aaedc057e",
            result.requestId,
        )
        assertEquals(
            "9deb6b8c-b797-4b34-9201-776ae1d3cf58",
            result.account?.ownerId,
        )
    }

    companion object {
        const val PARTITION = 0
    }
}


