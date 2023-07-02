package aigt.finaccounts.common.app.plugins

import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.app.routes.v1.accountTransactionV1Routing
import aigt.finaccounts.common.app.routes.v1.accountV1Routing
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(processor: AccountProcessor) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("api-kmp/v1") {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }

            accountV1Routing(processor)
            accountTransactionV1Routing(processor)
        }
    }
}
