package aigt.finaccounts.app.kafka.fixture

import aigt.finaccounts.app.kafka.AppKafkaConfig
import aigt.finaccounts.app.kafka.consumer.AppKafkaConsumer
import aigt.finaccounts.app.kafka.consumer.strategy.ConsumerStrategyV1Jvm
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*


class TestKafkaAppBuilder {

    val consumer = MockConsumer<String, String>(
        OffsetResetStrategy.EARLIEST,
    )

    var producer = MockProducer<String, String>(
        true,
        StringSerializer(),
        StringSerializer(),
    )

    var config = AppKafkaConfig()

    var inputTopic = config.kafkaTopicInV1Jvm

    var recordValue: String = ""

    companion object {
        const val PARTITION = 0
    }
}

fun TestKafkaAppBuilder.build(f: TestKafkaAppBuilder.() -> Unit): AppKafkaConsumer {
    f()

    val app = AppKafkaConsumer(
        config = config,
        consumerStrategies = listOf(ConsumerStrategyV1Jvm()),
        consumer = consumer,
        producer = producer,
    )

    consumer.schedulePollTask {
        consumer.rebalance(
            Collections.singletonList(
                TopicPartition(inputTopic, TestKafkaAppBuilder.PARTITION),
            ),
        )
        consumer.addRecord(
            ConsumerRecord(
                inputTopic,
                TestKafkaAppBuilder.PARTITION,
                0L,
                "test-1",
                recordValue,
            ),
        )
        app.stop()
    }

    val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
    val tp = TopicPartition(inputTopic, TestKafkaAppBuilder.PARTITION)
    startOffsets[tp] = 0L
    consumer.updateBeginningOffsets(startOffsets)

    return app
}
