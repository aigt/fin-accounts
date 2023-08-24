package aigt.finaccounts.app.ktor.jvm

import aigt.finaccounts.app.ktor.common.AppSettings
import aigt.finaccounts.app.ktor.common.plugins.initAppSettings
import aigt.finaccounts.app.ktor.jvm.plugins.configureCORS
import aigt.finaccounts.app.ktor.jvm.plugins.configureLogging
import aigt.finaccounts.app.ktor.jvm.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.defaultheaders.*
import aigt.finaccounts.app.ktor.common.module as commonModule

// function with config (application.conf)
fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module(appSettings: AppSettings = initAppSettings()) {
    val processor = appSettings.processor

    commonModule(appSettings)

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)

    configureCORS()
    configureLogging()

    @Suppress("OPT_IN_USAGE")
    install(Locations)

    configureRouting(appSettings)
}
