package aigt.finaccounts.api.v1.kmp.responses

import aigt.finaccounts.api.v1.kmp.accountResponseSerializer
import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.api.v1.kmp.models.IResponse

fun apiV1ResponseSerialize(response: IResponse): String =
    apiV1Mapper.encodeToString(accountResponseSerializer, response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV1ResponseDeserialize(json: String): T =
    apiV1Mapper.decodeFromString(accountResponseSerializer, json) as T
