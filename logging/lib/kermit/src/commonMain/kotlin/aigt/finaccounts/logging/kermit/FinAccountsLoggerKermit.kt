package aigt.finaccounts.logging.kermit

import aigt.finaccounts.logging.common.FinAccountsLoggerWrapper
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import kotlin.reflect.KClass

@Suppress("unused")
fun mpLoggerKermit(loggerId: String): FinAccountsLoggerWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV",
    )
    return FinAccountsLoggerWrapperKermit(
        logger = logger,
        loggerId = loggerId,
    )
}

@Suppress("unused")
fun mpLoggerKermit(cls: KClass<*>): FinAccountsLoggerWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV",
    )
    return FinAccountsLoggerWrapperKermit(
        logger = logger,
        loggerId = cls.qualifiedName ?: "",
    )
}
