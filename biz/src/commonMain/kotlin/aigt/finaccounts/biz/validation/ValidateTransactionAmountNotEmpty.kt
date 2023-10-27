package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Проверка, что сумма транзакции указана
 */
fun ICorChainDsl<FinAccountsContext>.validateTransactionAmountNotEmpty(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что сумма не указана
    on { transactionValidating.amount == TransactionAmount.NONE }

    // Информирование о проблеме
    handle {
        fail(
            errorValidation(
                field = "transactionAmount",
                violationCode = "empty",
                description = "field must not be empty",
            ),
        )
    }

}
