package aigt.finaccounts.app.ktor.common

import aigt.finaccounts.app.ktor.common.plugins.configureRouting
import aigt.finaccounts.app.ktor.common.plugins.initAppSettings
import io.ktor.server.application.*


fun Application.module(appSettings: AppSettings = initAppSettings()) {
    configureRouting(appSettings.processor)

    log.info("KMP module loaded")
}
