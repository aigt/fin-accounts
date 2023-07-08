package aigt.finaccounts.jvm.app.handlers.v1

import aigt.finaccounts.api.v1.jackson.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.jackson.models.AccountTransactRequest
import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.mappers.jvm.v1.fromTransport
import aigt.finaccounts.mappers.jvm.v1.toTransportHistory
import aigt.finaccounts.mappers.jvm.v1.toTransportTransact
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun ApplicationCall.historyAccount(processor: AccountProcessor) {
    val request = receive<AccountHistoryRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(context.toTransportHistory())
}

suspend fun ApplicationCall.transactAccount(processor: AccountProcessor) {
    val request = receive<AccountTransactRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(context.toTransportTransact())
}
