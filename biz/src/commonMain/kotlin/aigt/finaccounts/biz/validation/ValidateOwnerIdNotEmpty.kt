package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Проверка, что идентификатор владельца счёта не пуст
 */
fun ICorChainDsl<FinAccountsContext>.validateOwnerIdNotEmpty(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что идентификатор пустой
    on { accountValidating.ownerId.isEmpty() }

    // Информирование о проблеме
    handle {
        fail(
            errorValidation(
                field = "ownerId",
                violationCode = "empty",
                description = "field must not be empty",
            ),
        )
    }

}
