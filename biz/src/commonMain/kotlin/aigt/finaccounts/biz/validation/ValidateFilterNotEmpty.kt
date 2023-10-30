package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Проверка, что фильтр не пуст
 */
fun ICorChainDsl<FinAccountsContext>.validateFilterNotEmpty(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что идентификатор пустой
    on { accountFilterValidating == AccountFilter.NONE }

    // Информирование о проблеме
    handle {
        fail(
            errorValidation(
                field = "accountFilter",
                violationCode = "empty",
                description = "field must not be empty",
            ),
        )
    }

}
