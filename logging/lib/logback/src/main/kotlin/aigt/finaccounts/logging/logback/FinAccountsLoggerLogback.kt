package aigt.finaccounts.logging.logback

import aigt.finaccounts.logging.common.FinAccountsLoggerWrapper
import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass


/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun mpLoggerLogback(logger: Logger): FinAccountsLoggerWrapper =
    FinAccountsLogWrapperLogback(
        logger = logger,
        loggerId = logger.name,
    )


fun mpLoggerLogback(clazz: KClass<*>): FinAccountsLoggerWrapper =
    mpLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)


@Suppress("unused")
fun mpLoggerLogback(loggerId: String): FinAccountsLoggerWrapper =
    mpLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
