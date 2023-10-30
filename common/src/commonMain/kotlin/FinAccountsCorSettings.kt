package aigt.finaccounts.common

import aigt.finaccounts.logging.common.FinAccountsLoggerProvider

data class FinAccountsCorSettings(
    val loggerProvider: FinAccountsLoggerProvider = FinAccountsLoggerProvider(),
) {
    companion object {
        val NONE = FinAccountsCorSettings()
    }
}
