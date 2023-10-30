package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountStatus
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка Статуса валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanStatus(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        accountValidating.status = AccountStatus.NONE
    }

}
