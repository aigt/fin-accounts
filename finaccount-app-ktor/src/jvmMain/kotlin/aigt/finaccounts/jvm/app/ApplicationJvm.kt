package aigt.finaccounts.jvm.app

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.jvm.app.plugins.configureCORS
import aigt.finaccounts.jvm.app.plugins.configureLogging
import aigt.finaccounts.jvm.app.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.defaultheaders.*
import aigt.finaccounts.common.app.module as commonModule

// function with config (application.conf)
fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.moduleJvm() {
    val processor = AccountProcessor()

    commonModule(processor)

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)

    configureCORS()
    configureLogging()

    @Suppress("OPT_IN_USAGE")
    install(Locations)

    configureRouting(processor)
}
