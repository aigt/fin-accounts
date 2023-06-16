package aigt.api.v1.kmp.responses

import aigt.api.v1.kmp.accountResponseSerializer
import aigt.api.v1.kmp.apiV2Mapper
import aigt.api.v1.kmp.models.IResponse

fun apiV2ResponseSerialize(response: IResponse): String =
    apiV2Mapper.encodeToString(accountResponseSerializer, response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2ResponseDeserialize(json: String): T =
    apiV2Mapper.decodeFromString(accountResponseSerializer, json) as T
