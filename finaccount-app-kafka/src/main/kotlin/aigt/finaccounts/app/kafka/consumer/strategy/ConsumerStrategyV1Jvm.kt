package aigt.finaccounts.app.kafka.consumer.strategy

import aigt.finaccounts.api.v1.jackson.apiV1RequestDeserialize
import aigt.finaccounts.api.v1.jackson.apiV1ResponseSerialize
import aigt.finaccounts.api.v1.jackson.models.IRequest
import aigt.finaccounts.api.v1.jackson.models.IResponse
import aigt.finaccounts.app.kafka.AppKafkaConfig
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.mappers.jvm.v1.fromTransport
import aigt.finaccounts.mappers.jvm.v1.toTransportResponse


class ConsumerStrategyV1Jvm : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(
            config.kafkaTopicInV1Jvm,
            config.kafkaTopicOutV1Jvm,
        )
    }

    override fun serialize(source: FinAccountsContext): String {
        val response: IResponse = source.toTransportResponse()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: FinAccountsContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
