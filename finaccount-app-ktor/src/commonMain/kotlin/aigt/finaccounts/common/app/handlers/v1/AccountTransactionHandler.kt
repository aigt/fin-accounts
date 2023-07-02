package aigt.finaccounts.common.app.handlers.v1

import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.kmp.models.AccountReadRequest
import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.mappers.kmp.v1.fromTransport
import aigt.finaccounts.mappers.kmp.v1.toTransportHistory
import aigt.finaccounts.mappers.kmp.v1.toTransportTransact
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

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
        apiV1Mapper.decodeFromString<AccountReadRequest>(receiveText())
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(apiV1Mapper.encodeToString(context.toTransportTransact()))
}
