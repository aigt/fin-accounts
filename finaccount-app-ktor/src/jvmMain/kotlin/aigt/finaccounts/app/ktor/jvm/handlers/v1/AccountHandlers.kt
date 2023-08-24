package aigt.finaccounts.app.ktor.jvm.handlers.v1

import aigt.finaccounts.api.v1.jackson.models.AccountCreateRequest
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.jackson.models.AccountReadRequest
import aigt.finaccounts.api.v1.jackson.models.AccountSearchRequest
import aigt.finaccounts.api.v1.jackson.models.AccountTransactRequest
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateRequest
import aigt.finaccounts.app.ktor.common.AppSettings
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.mappers.jvm.v1.fromTransport
import aigt.finaccounts.mappers.jvm.v1.toTransportCreate
import aigt.finaccounts.mappers.jvm.v1.toTransportHistory
import aigt.finaccounts.mappers.jvm.v1.toTransportRead
import aigt.finaccounts.mappers.jvm.v1.toTransportSearch
import aigt.finaccounts.mappers.jvm.v1.toTransportTransact
import aigt.finaccounts.mappers.jvm.v1.toTransportUpdate
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

/*
suspend inline fun <reified Q : IRequest, reified R : IResponse> ApplicationCall.processV1(
    processor: AccountProcessor,
    request: Q,
    logger: FinAccountsLoggerWrapper,
    logId: String,
) {
    processor.exec(logger, logId,
        fromTransport = { fromTransport(request) },
        sendResponse = { respond(toTransportAd()) },
        toLog = { toLog(logId) },
    )
}*/


suspend fun ApplicationCall.createAccount(appSettings: AppSettings) {
    val request = receive<AccountCreateRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    appSettings.processor.exec(context)

    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readAccount(appSettings: AppSettings) {
    val request = receive<AccountReadRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    appSettings.processor.exec(context)

    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateAccount(appSettings: AppSettings) {
    val request = receive<AccountUpdateRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    appSettings.processor.exec(context)

    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.searchAccount(appSettings: AppSettings) {
    val request = receive<AccountSearchRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    appSettings.processor.exec(context)

    respond(context.toTransportSearch())
}

suspend fun ApplicationCall.historyAccount(appSettings: AppSettings) {
    val request = receive<AccountHistoryRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    appSettings.processor.exec(context)

    respond(context.toTransportHistory())
}

suspend fun ApplicationCall.transactAccount(appSettings: AppSettings) {
    val request = receive<AccountTransactRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    appSettings.processor.exec(context)

    respond(context.toTransportTransact())
}
