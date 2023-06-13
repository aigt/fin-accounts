package aigt.api.kmp.v1.responses

import aigt.api.kmp.v1.AdResponseSerializer
import aigt.api.kmp.v1.apiV2Mapper
import aigt.api.kmp.v1.models.IResponse

fun apiV2ResponseSerialize(response: IResponse): String
    = apiV2Mapper.encodeToString(AdResponseSerializer, response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2ResponseDeserialize(json: String): T
    = apiV2Mapper.decodeFromString(AdResponseSerializer, json) as T
