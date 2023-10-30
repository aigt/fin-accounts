package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Копирование аккаунта для валидации
 */
fun ICorChainDsl<FinAccountsContext>.validatingCopyAccount(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Копирование
    handle {
        accountValidating = accountRequest.deepCopy()
    }

}
