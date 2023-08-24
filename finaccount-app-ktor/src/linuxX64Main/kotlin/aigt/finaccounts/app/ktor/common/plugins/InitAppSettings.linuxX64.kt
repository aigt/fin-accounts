package aigt.finaccounts.app.ktor.common.plugins

import aigt.finaccounts.logging.common.FinAccountsLoggerProvider
import io.ktor.server.application.*

actual fun Application.getLoggerProviderConf(): FinAccountsLoggerProvider =
    getKMPLoggerProviderConf()
