package aigt.finaccounts.api.v1.kmp.requests

import aigt.finaccounts.api.v1.kmp.accountRequestSerializer
import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.api.v1.kmp.models.IRequest

fun apiV1RequestSerialize(request: IRequest): String =
    apiV1Mapper.encodeToString(accountRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2RequestDeserialize(json: String): T =
    apiV1Mapper.decodeFromString(accountRequestSerializer, json) as T
