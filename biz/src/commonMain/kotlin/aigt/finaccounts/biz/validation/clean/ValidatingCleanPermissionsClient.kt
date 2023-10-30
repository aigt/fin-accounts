package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка разрешений валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingCleanPermissionsClient(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Очистка
    handle {
        accountValidating.permissionsClient.clear()
    }

}
