package aigt.finaccounts.app.routes.v1

import aigt.finaccounts.app.handlers.v1.createAccount
import aigt.finaccounts.app.handlers.v1.readAccount
import aigt.finaccounts.app.handlers.v1.searchAccount
import aigt.finaccounts.app.handlers.v1.updateAccount
import aigt.finaccounts.biz.AccountProcessor
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.accountV1Routing(processor: AccountProcessor) {
    route("account") {
        post("create") {
            call.createAccount(processor)
        }
        post("read") {
            call.readAccount(processor)
        }
        post("update") {
            call.updateAccount(processor)
        }
        post("search") {
            call.searchAccount(processor)
        }
    }
}
