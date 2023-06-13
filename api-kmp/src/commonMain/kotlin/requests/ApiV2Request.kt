package aigt.api.kmp.v1.requests

import aigt.api.kmp.v1.AdRequestSerializer
import aigt.api.kmp.v1.apiV2Mapper
import aigt.api.kmp.v1.models.IRequest

fun apiV2RequestSerialize(request: IRequest): String
    = apiV2Mapper.encodeToString(AdRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2RequestDeserialize(json: String): T
    = apiV2Mapper.decodeFromString(AdRequestSerializer, json) as T
