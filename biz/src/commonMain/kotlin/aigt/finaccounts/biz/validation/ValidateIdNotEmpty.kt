package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Проверка, что идентификатор счёта не пуст
 */
fun ICorChainDsl<FinAccountsContext>.validateIdNotEmpty(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что идентификатор пустой
    on { accountValidating.id == AccountId.NONE }

    // Информирование о проблеме
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty",
            ),
        )
    }

}
