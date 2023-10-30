package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка пустых символов в начале и конце описания валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingTrimDescription(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        accountValidating.description = accountValidating.description.trim()
    }

}
