package aigt.finaccounts.app.ktor.common

import aigt.finaccounts.biz.aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.logging.common.FinAccountsLoggerProvider


data class AppSettings(
    val appUrls: List<String> = emptyList(),
    val processor: AccountProcessor = AccountProcessor(),
    val logger: FinAccountsLoggerProvider,
)
