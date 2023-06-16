package aigt.api.v1.kmp.requests

import aigt.api.v1.kmp.accountRequestSerializer
import aigt.api.v1.kmp.apiV2Mapper
import aigt.api.v1.kmp.models.IRequest

fun apiV2RequestSerialize(request: IRequest): String =
    apiV2Mapper.encodeToString(accountRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2RequestDeserialize(json: String): T =
    apiV2Mapper.decodeFromString(accountRequestSerializer, json) as T
