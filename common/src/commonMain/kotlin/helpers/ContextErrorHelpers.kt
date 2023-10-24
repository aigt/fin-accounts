package aigt.finaccounts.common.helpers

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.state.ContextState

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

fun FinAccountsContext.fail(error: ContextError) {
    addError(error)
    state = ContextState.FAILING
}

/**
 * @param violationCode Код, характеризующий ошибку. Не должен включать имя
 * поля или указание на валидацию. Например: empty, badSymbols, tooLong, etc
 */
fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: ContextError.Level = ContextError.Level.ERROR,
) = ContextError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)
