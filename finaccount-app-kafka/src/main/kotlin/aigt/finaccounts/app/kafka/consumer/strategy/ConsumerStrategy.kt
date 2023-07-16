package aigt.finaccounts.app.kafka.consumer.strategy

import aigt.finaccounts.app.kafka.AppKafkaConfig
import aigt.finaccounts.common.FinAccountsContext


data class InputOutputTopics(val input: String, val output: String)

interface ConsumerStrategy {
    fun topics(config: AppKafkaConfig): InputOutputTopics
    fun serialize(source: FinAccountsContext): String
    fun deserialize(value: String, target: FinAccountsContext)
}
