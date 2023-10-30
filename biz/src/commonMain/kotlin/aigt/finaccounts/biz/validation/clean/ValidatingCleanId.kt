package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка идентификатора валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanId(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        accountValidating.id = AccountId.NONE
    }

}
