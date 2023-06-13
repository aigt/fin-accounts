package aigt.api.kmp.v1

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import aigt.api.kmp.v1.models.*

@OptIn(ExperimentalSerializationApi::class)
val apiV2Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is AccountCreateRequest   ->  RequestSerializer(AccountCreateRequest  .serializer()) as SerializationStrategy<IRequest>
                is AccountReadRequest     ->  RequestSerializer(AccountReadRequest    .serializer()) as SerializationStrategy<IRequest>
                is AccountUpdateRequest   ->  RequestSerializer(AccountUpdateRequest  .serializer()) as SerializationStrategy<IRequest>
                is AccountHistoryRequest  ->  RequestSerializer(AccountHistoryRequest .serializer()) as SerializationStrategy<IRequest>
                is AccountSearchRequest   ->  RequestSerializer(AccountSearchRequest  .serializer()) as SerializationStrategy<IRequest>
                is AccountTransactRequest ->  RequestSerializer(AccountTransactRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class) {
            AdRequestSerializer
        }
        polymorphicDefaultSerializer(IResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is AccountCreateResponse   ->  ResponseSerializer(AccountCreateResponse  .serializer()) as SerializationStrategy<IResponse>
                is AccountReadResponse     ->  ResponseSerializer(AccountReadResponse    .serializer()) as SerializationStrategy<IResponse>
                is AccountUpdateResponse   ->  ResponseSerializer(AccountUpdateResponse  .serializer()) as SerializationStrategy<IResponse>
                is AccountHistoryResponse  ->  ResponseSerializer(AccountHistoryResponse .serializer()) as SerializationStrategy<IResponse>
                is AccountSearchResponse   ->  ResponseSerializer(AccountSearchResponse  .serializer()) as SerializationStrategy<IResponse>
                is AccountTransactResponse ->  ResponseSerializer(AccountTransactResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }
        polymorphicDefault(IResponse::class) {
            AdResponseSerializer
        }

        contextual(AdRequestSerializer)
        contextual(AdResponseSerializer)
    }
}

fun Json.encodeResponse(response: IResponse): String = encodeToString(AdResponseSerializer, response)

fun apiV2ResponseSerialize(Response: IResponse): String = apiV2Mapper.encodeToString(AdResponseSerializer, Response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2ResponseDeserialize(json: String): T = apiV2Mapper.decodeFromString(AdResponseSerializer, json) as T

fun apiV2RequestSerialize(request: IRequest): String = apiV2Mapper.encodeToString(AdRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2RequestDeserialize(json: String): T = apiV2Mapper.decodeFromString(AdRequestSerializer, json) as T
