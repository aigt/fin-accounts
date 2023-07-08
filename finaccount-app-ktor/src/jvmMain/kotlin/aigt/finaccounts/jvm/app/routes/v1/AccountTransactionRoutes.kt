package aigt.finaccounts.jvm.app.routes.v1

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.jvm.app.handlers.v1.historyAccount
import aigt.finaccounts.jvm.app.handlers.v1.transactAccount
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
