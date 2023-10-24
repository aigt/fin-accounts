package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Проверка, что указана валюта счёта
 */
fun ICorChainDsl<FinAccountsContext>.validateCurrencyNotEmpty(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что идентификатор пустой
    on { accountValidating.currency == AccountCurrency.NONE }

    // Информирование о проблеме
    handle {
        fail(
            errorValidation(
                field = "currency",
                violationCode = "empty",
                description = "field must not be empty",
            ),
        )
    }

}
