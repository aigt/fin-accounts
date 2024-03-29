package aigt.finaccounts.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class FinAccountsLoggerProvider(
    private val provider: (String) -> FinAccountsLoggerWrapper = { FinAccountsLoggerWrapper.DEFAULT },
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) =
        provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")

    fun logger(function: KFunction<*>) = provider(function.name)
}
