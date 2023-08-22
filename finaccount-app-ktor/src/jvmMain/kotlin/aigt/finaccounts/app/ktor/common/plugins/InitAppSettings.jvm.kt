package aigt.finaccounts.app.ktor.common.plugins

import aigt.finaccounts.logging.common.FinAccountsLoggerProvider
import aigt.finaccounts.logging.kermit.mpLoggerKermit
import aigt.finaccounts.logging.logback.mpLoggerLogback
import io.ktor.server.application.*


actual fun Application.getLoggerProviderConf(): FinAccountsLoggerProvider {

    val mode = environment.config.propertyOrNull("ktor.logger")
        ?.getString()

    return when (mode) {
        "kmp", "keremit" -> FinAccountsLoggerProvider { mpLoggerKermit(it) }
        "logback", null -> FinAccountsLoggerProvider { mpLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Admitted values are kmp and logback")
    }
}



