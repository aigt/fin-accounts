package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Копирование номера счёта в транзакцию
 */
fun ICorChainDsl<FinAccountsContext>.validatingCopyTransactionAccountId(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        transactionValidating.accountId =
            TransactionAccountId(accountValidating.id.asString())
    }

}
