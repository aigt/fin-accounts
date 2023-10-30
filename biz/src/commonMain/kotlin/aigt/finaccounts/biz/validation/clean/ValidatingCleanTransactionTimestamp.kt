package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.transaction.TransactionTimestamp
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**s
 * Очистка метки времени валидируемой транзакции
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanTransactionTimestamp(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        transactionValidating.timestamp = TransactionTimestamp.NONE
    }

}
