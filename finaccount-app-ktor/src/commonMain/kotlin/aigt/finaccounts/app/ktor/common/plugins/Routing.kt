package aigt.finaccounts.app.ktor.common.plugins

import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.app.ktor.common.handlers.v1.createAccount
import aigt.finaccounts.app.ktor.common.handlers.v1.historyAccount
import aigt.finaccounts.app.ktor.common.handlers.v1.readAccount
import aigt.finaccounts.app.ktor.common.handlers.v1.searchAccount
import aigt.finaccounts.app.ktor.common.handlers.v1.transactAccount
import aigt.finaccounts.app.ktor.common.handlers.v1.updateAccount
import aigt.finaccounts.biz.AccountProcessor
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(processor: AccountProcessor) {
    routing {
        get("/") { call.respondText("Hello, world!") }
        route("api-kmp/v1") {
            install(ContentNegotiation) {
                json(apiV1Mapper, ContentType.Application.Json)
            }
            accountV1Routing(processor)
        }
    }
}


fun Route.accountV1Routing(processor: AccountProcessor) {
    route("account") {
        post("create") { call.createAccount(processor) }
        post("read") { call.readAccount(processor) }
        post("update") { call.updateAccount(processor) }
        post("search") { call.searchAccount(processor) }
        post("history") { call.historyAccount(processor) }
        post("transact") { call.transactAccount(processor) }
    }
}
