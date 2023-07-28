package aigt.finaccounts.app.kafka

import aigt.finaccounts.app.kafka.consumer.AppKafkaConsumer
import aigt.finaccounts.app.kafka.consumer.strategy.ConsumerStrategyV1Jvm
import aigt.finaccounts.app.kafka.consumer.strategy.ConsumerStrategyV1Kmp

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(
        config,
        listOf(ConsumerStrategyV1Jvm(), ConsumerStrategyV1Kmp()),
    )
    consumer.run()
}
