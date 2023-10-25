package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка времени последней транзакции валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanLastTransactionTime(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        accountValidating.lastTransactionTime = AccountLastTransactionTime.NONE
    }

}
