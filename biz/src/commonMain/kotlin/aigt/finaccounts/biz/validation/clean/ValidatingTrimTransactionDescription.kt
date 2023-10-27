package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка пустых символов в начале и конце описания валидируемой транзакции
 */
fun ICorChainDsl<FinAccountsContext>.validatingTrimTransactionDescription(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        transactionValidating.description =
            transactionValidating.description.trim()
    }

}
