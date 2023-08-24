package aigt.finaccounts.app.ktor.common.plugins

import io.ktor.server.application.*


actual fun Application.getLoggerProviderConf() = getKMPLoggerProviderConf()
