package aigt.finaccounts.common.helpers

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.error.ContextError

fun Throwable.asAccountsError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = ContextError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun FinAccountsContext.addError(vararg error: ContextError) =
    errors.addAll(error)
