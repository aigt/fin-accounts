package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Копирование транзакции для валидации
 */
fun ICorChainDsl<FinAccountsContext>.validatingCopyTransaction(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Копирование
    handle {
        transactionValidating = transactionRequest.deepCopy()
    }

}
