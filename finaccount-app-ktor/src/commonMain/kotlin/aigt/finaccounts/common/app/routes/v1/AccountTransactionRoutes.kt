package aigt.finaccounts.common.app.routes.v1

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.app.handlers.v1.historyAccount
import aigt.finaccounts.common.app.handlers.v1.transactAccount
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.accountTransactionV1Routing(processor: AccountProcessor) {
    route("account") {
        post("history") {
            call.historyAccount(processor)
        }
        post("transact") {
            call.transactAccount(processor)
        }
    }
}
