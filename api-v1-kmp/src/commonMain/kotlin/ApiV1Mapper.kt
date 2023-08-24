package aigt.finaccounts.api.v1.kmp

import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountCreateResponse
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryResponse
import aigt.finaccounts.api.v1.kmp.models.AccountReadRequest
import aigt.finaccounts.api.v1.kmp.models.AccountReadResponse
import aigt.finaccounts.api.v1.kmp.models.AccountSearchRequest
import aigt.finaccounts.api.v1.kmp.models.AccountSearchResponse
import aigt.finaccounts.api.v1.kmp.models.AccountTransactRequest
import aigt.finaccounts.api.v1.kmp.models.AccountTransactResponse
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateResponse
import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.models.IResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

@OptIn(ExperimentalSerializationApi::class)
val apiV1Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {

        polymorphicDefaultSerializer(IRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when (it) {
                is AccountCreateRequest -> RequestSerializer(
                    AccountCreateRequest.serializer(),
                ) as SerializationStrategy<IRequest>

                is AccountReadRequest -> RequestSerializer(
                    AccountReadRequest.serializer(),
                ) as SerializationStrategy<IRequest>

                is AccountUpdateRequest -> RequestSerializer(
                    AccountUpdateRequest.serializer(),
                ) as SerializationStrategy<IRequest>

                is AccountHistoryRequest -> RequestSerializer(
                    AccountHistoryRequest.serializer(),
                ) as SerializationStrategy<IRequest>

                is AccountSearchRequest -> RequestSerializer(
                    AccountSearchRequest.serializer(),
                ) as SerializationStrategy<IRequest>

                is AccountTransactRequest -> RequestSerializer(
                    AccountTransactRequest.serializer(),
                ) as SerializationStrategy<IRequest>

                else -> null
            }
        }

        polymorphicDefaultDeserializer(IRequest::class) {
            accountRequestSerializer
        }

        polymorphicDefaultSerializer(IResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when (it) {
                is AccountCreateResponse -> ResponseSerializer(
                    AccountCreateResponse.serializer(),
                ) as SerializationStrategy<IResponse>

                is AccountReadResponse -> ResponseSerializer(
                    AccountReadResponse.serializer(),
                ) as SerializationStrategy<IResponse>

                is AccountUpdateResponse -> ResponseSerializer(
                    AccountUpdateResponse.serializer(),
                ) as SerializationStrategy<IResponse>

                is AccountHistoryResponse -> ResponseSerializer(
                    AccountHistoryResponse.serializer(),
                ) as SerializationStrategy<IResponse>

                is AccountSearchResponse -> ResponseSerializer(
                    AccountSearchResponse.serializer(),
                ) as SerializationStrategy<IResponse>

                is AccountTransactResponse -> ResponseSerializer(
                    AccountTransactResponse.serializer(),
                ) as SerializationStrategy<IResponse>

                else -> null
            }
        }

        polymorphicDefaultDeserializer(IResponse::class) {
            accountResponseSerializer
        }

        contextual(accountRequestSerializer)
        contextual(accountResponseSerializer)
    }
}

fun Json.encodeResponse(response: IResponse): String =
    encodeToString(accountResponseSerializer, response)
