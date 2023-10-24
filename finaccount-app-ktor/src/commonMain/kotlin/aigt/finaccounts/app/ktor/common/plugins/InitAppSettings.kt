package aigt.finaccounts.app.ktor.common.plugins

import aigt.finaccounts.app.ktor.common.AppSettings
import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.logging.common.FinAccountsLoggerProvider
import aigt.finaccounts.logging.kermit.mpLoggerKermit
import io.ktor.server.application.*

fun Application.initAppSettings(): AppSettings {
    return AppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList()
            ?: emptyList(),
        processor = AccountProcessor(),
        logger = getLoggerProviderConf(),
    )
}


expect fun Application.getLoggerProviderConf(): FinAccountsLoggerProvider


fun getKMPLoggerProviderConf() = FinAccountsLoggerProvider {
    mpLoggerKermit(it)
}
