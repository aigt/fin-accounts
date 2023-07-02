package aigt.finaccounts.app.handlers.v1

import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountReadRequest
import aigt.finaccounts.api.v1.kmp.models.AccountSearchRequest
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateRequest
import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.mappers.v1.fromTransport
import aigt.finaccounts.mappers.v1.toTransportCreate
import aigt.finaccounts.mappers.v1.toTransportRead
import aigt.finaccounts.mappers.v1.toTransportSearch
import aigt.finaccounts.mappers.v1.toTransportUpdate
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

suspend fun ApplicationCall.createAccount(processor: AccountProcessor) {
    val request =
        apiV1Mapper.decodeFromString<AccountCreateRequest>(receiveText())
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(apiV1Mapper.encodeToString(context.toTransportCreate()))
}

suspend fun ApplicationCall.readAccount(processor: AccountProcessor) {
    val request =
        apiV1Mapper.decodeFromString<AccountReadRequest>(receiveText())
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(apiV1Mapper.encodeToString(context.toTransportRead()))
}

suspend fun ApplicationCall.updateAccount(processor: AccountProcessor) {
    val request =
        apiV1Mapper.decodeFromString<AccountUpdateRequest>(receiveText())
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(apiV1Mapper.encodeToString(context.toTransportUpdate()))
}

suspend fun ApplicationCall.searchAccount(processor: AccountProcessor) {
    val request =
        apiV1Mapper.decodeFromString<AccountSearchRequest>(receiveText())
    val context = FinAccountsContext()
    context.fromTransport(request)
    processor.exec(context)

    respond(apiV1Mapper.encodeToString(context.toTransportSearch()))
}
