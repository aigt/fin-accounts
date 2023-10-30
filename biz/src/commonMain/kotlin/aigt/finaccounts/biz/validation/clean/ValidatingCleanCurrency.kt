package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка валюты валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanCurrency(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        accountValidating.currency = AccountCurrency.NONE
    }

}
