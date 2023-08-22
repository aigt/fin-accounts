package aigt.finaccounts.app.ktor.jvm.plugins

import aigt.finaccounts.api.v1.jackson.apiV1Mapper
import aigt.finaccounts.app.ktor.common.AppSettings
import aigt.finaccounts.app.ktor.jvm.handlers.v1.createAccount
import aigt.finaccounts.app.ktor.jvm.handlers.v1.historyAccount
import aigt.finaccounts.app.ktor.jvm.handlers.v1.readAccount
import aigt.finaccounts.app.ktor.jvm.handlers.v1.searchAccount
import aigt.finaccounts.app.ktor.jvm.handlers.v1.transactAccount
import aigt.finaccounts.app.ktor.jvm.handlers.v1.updateAccount
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(appSettings: AppSettings) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("api/v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }
            accountV1Routing(appSettings)
        }
        staticResources("/", "files") {
            default("index.html")
        }
    }
}


fun Route.accountV1Routing(appSettings: AppSettings) {
    route("account") {
        post("create") { call.createAccount(appSettings) }
        post("read") { call.readAccount(appSettings) }
        post("update") { call.updateAccount(appSettings) }
        post("search") { call.searchAccount(appSettings) }
        post("history") { call.historyAccount(appSettings) }
        post("transact") { call.transactAccount(appSettings) }
    }
}
