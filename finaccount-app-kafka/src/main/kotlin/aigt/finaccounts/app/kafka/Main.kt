package aigt.finaccounts.app.kafka

import aigt.finaccounts.app.kafka.consumer.AppKafkaConsumer
import aigt.finaccounts.app.kafka.consumer.strategy.ConsumerStrategyV1Jvm
import aigt.finaccounts.app.kafka.consumer.strategy.ConsumerStrategyV1Kmp
import aigt.finaccounts.biz.AccountProcessor

fun main() {
    val config = AppKafkaConfig()
    /*val loggerProvider = FinAccountsLoggerProvider { mpLoggerLogback(it) }
    val corSettings = FinAccountsCorSettings(loggerProvider = loggerProvider)
    val processor = AccountProcessor(corSettings = corSettings)*/
    val consumer = AppKafkaConsumer(
        config = config,
        consumerStrategies = listOf(
            ConsumerStrategyV1Jvm(),
            ConsumerStrategyV1Kmp(),
        ),
        // processor = processor,
        processor = AccountProcessor(),
    )
    consumer.run()
}
