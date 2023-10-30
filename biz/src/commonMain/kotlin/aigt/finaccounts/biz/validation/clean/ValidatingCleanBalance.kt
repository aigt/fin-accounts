package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка баланса валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanBalance(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        accountValidating.balance = AccountBalance.NONE
    }

}
