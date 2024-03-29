package aigt.finaccounts.api.v1.jackson

import aigt.finaccounts.api.v1.jackson.models.IRequest
import aigt.finaccounts.api.v1.jackson.models.IResponse
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper


// mixin for BaseType to define @JsonTypeInfo
// this can be in a completely different package / module
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "requestType",
    visible = true,
)
abstract class AccountCreateRequestMixIn


val apiV1Mapper: JsonMapper = JsonMapper.builder().run {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)

    build()
}

fun apiV1RequestSerialize(request: IRequest): String =
    apiV1Mapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV1RequestDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IRequest::class.java) as T

fun apiV1ResponseSerialize(response: IResponse): String =
    apiV1Mapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponse> apiV1ResponseDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IResponse::class.java) as T
