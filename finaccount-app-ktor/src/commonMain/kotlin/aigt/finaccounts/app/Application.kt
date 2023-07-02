package aigt.finaccounts.app

import aigt.finaccounts.app.plugins.configureRouting
import aigt.finaccounts.biz.AccountProcessor
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun Application.module(processor: AccountProcessor = AccountProcessor()) {
    configureRouting(processor)
    
    log.info("KMP module loaded")
}

fun main() {
    embeddedServer(CIO, port = 8080) {
        module()
    }.start(wait = true)
}
