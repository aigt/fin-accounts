package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Копирование параметров поиска для валидации
 */
fun ICorChainDsl<FinAccountsContext>.validatingCopyFilter(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Копирование
    handle {
        accountFilterValidating = accountFilter.deepCopy()
    }

}
