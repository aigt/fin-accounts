package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Проверка, что идентификатор счёта ответного контрагента по операции не пуст
 */
fun ICorChainDsl<FinAccountsContext>.validateTransactionCounterpartyNotEmpty(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что идентификатор пустой
    on { transactionValidating.counterparty == TransactionCounterparty.NONE }

    // Информирование о проблеме
    handle {
        fail(
            errorValidation(
                field = "transactionCounterparty",
                violationCode = "empty",
                description = "field must not be empty",
            ),
        )
    }

}
