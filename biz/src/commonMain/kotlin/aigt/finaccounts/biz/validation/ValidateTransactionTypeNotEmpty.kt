package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.transaction.TransactionType
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Проверка, что тип транзакции указан
 */
fun ICorChainDsl<FinAccountsContext>.validateTransactionTypeNotEmpty(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что тип не указан
    on { transactionValidating.type == TransactionType.NONE }

    // Информирование о проблеме
    handle {
        fail(
            errorValidation(
                field = "transactionType",
                violationCode = "empty",
                description = "field must not be empty",
            ),
        )
    }

}
