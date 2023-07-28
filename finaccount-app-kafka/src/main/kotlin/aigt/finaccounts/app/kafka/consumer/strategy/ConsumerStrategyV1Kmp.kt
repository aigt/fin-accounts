package aigt.finaccounts.app.kafka.consumer.strategy

import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.models.IResponse
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestDeserialize
import aigt.finaccounts.api.v1.kmp.responses.apiV1ResponseSerialize
import aigt.finaccounts.app.kafka.AppKafkaConfig
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.mappers.kmp.v1.fromTransport
import aigt.finaccounts.mappers.kmp.v1.toTransportResponse

class ConsumerStrategyV1Kmp : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(
            config.kafkaTopicInV1Kmp,
            config.kafkaTopicOutV1Kmp,
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
