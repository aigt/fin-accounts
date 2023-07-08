package aigt.finaccounts.jvm.app.plugins

import aigt.finaccounts.api.v1.jackson.apiV1Mapper
import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.jvm.app.routes.v1.accountTransactionV1Routing
import aigt.finaccounts.jvm.app.routes.v1.accountV1Routing
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(processor: AccountProcessor) {
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

            accountV1Routing(processor)
            accountTransactionV1Routing(processor)
        }

        static("static") {
            resources("static")
        }
    }
}
