package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.transaction.TransactionId
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**s
 * Очистка идентификатора валидируемой транзакции
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanTransactionId(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        transactionValidating.id = TransactionId.NONE
    }

}
