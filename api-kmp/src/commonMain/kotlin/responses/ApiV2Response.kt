package aigt.api.kmp.v1.responses

import aigt.api.kmp.v1.accountResponseSerializer
import aigt.api.kmp.v1.apiV2Mapper
import aigt.api.kmp.v1.models.IResponse

fun apiV2ResponseSerialize(response: IResponse): String =
    apiV2Mapper.encodeToString(accountResponseSerializer, response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2ResponseDeserialize(json: String): T =
    apiV2Mapper.decodeFromString(accountResponseSerializer, json) as T
