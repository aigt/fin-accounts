package aigt.finaccounts.jvm.app.handlers.v1

import aigt.finaccounts.api.v1.jackson.models.AccountCreateRequest
import aigt.finaccounts.api.v1.jackson.models.AccountReadRequest
import aigt.finaccounts.api.v1.jackson.models.AccountSearchRequest
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateRequest
import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.mappers.jvm.v1.fromTransport
import aigt.finaccounts.mappers.jvm.v1.toTransportCreate
import aigt.finaccounts.mappers.jvm.v1.toTransportRead
import aigt.finaccounts.mappers.jvm.v1.toTransportSearch
import aigt.finaccounts.mappers.jvm.v1.toTransportUpdate
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun ApplicationCall.createAccount(processor: AccountProcessor) {
    val request = receive<AccountCreateRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readAccount(processor: AccountProcessor) {
    val request = receive<AccountReadRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateAccount(processor: AccountProcessor) {
    val request = receive<AccountUpdateRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.searchAccount(processor: AccountProcessor) {
    val request = receive<AccountSearchRequest>()
    val context = FinAccountsContext()

    context.fromTransport(request)
    processor.exec(context)

    respond(context.toTransportSearch())
}
