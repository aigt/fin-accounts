package aigt.finaccounts.app.ktor.common.handlers.v1

import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.kmp.models.AccountReadRequest
import aigt.finaccounts.api.v1.kmp.models.AccountSearchRequest
import aigt.finaccounts.api.v1.kmp.models.AccountTransactRequest
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateRequest
import aigt.finaccounts.biz.aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.mappers.kmp.v1.fromTransport
import aigt.finaccounts.mappers.kmp.v1.toTransportCreate
import aigt.finaccounts.mappers.kmp.v1.toTransportHistory
import aigt.finaccounts.mappers.kmp.v1.toTransportRead
import aigt.finaccounts.mappers.kmp.v1.toTransportSearch
import aigt.finaccounts.mappers.kmp.v1.toTransportTransact
import aigt.finaccounts.mappers.kmp.v1.toTransportUpdate
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
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


suspend fun ApplicationCall.historyAccount(processor: AccountProcessor) {
    val request =
        apiV1Mapper.decodeFromString<AccountHistoryRequest>(receiveText())
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(apiV1Mapper.encodeToString(context.toTransportHistory()))
}

suspend fun ApplicationCall.transactAccount(processor: AccountProcessor) {
    val request =
        apiV1Mapper.decodeFromString<AccountTransactRequest>(receiveText())
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(apiV1Mapper.encodeToString(context.toTransportTransact()))
}
