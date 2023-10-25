package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка идентификатора владельца счёта валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanOwnerId(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        accountValidating.ownerId = AccountOwnerId.NONE
    }

}
