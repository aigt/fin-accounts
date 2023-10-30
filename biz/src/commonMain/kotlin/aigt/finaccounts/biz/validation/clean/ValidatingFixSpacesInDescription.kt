package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка повторяющихся пробелов и замена пробельных символов на стандартный
 * в описании валидируемого аккаунта
 */
fun ICorChainDsl<FinAccountsContext>.validatingFixSpacesInDescription(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что описание не пустое
    on { accountValidating.description != AccountDescription.NONE }

    // Очистка
    handle {
        val fixedSpaces = accountValidating.description.asString()
            .replace(Regex("\\p{Space}+"), " ")
        accountValidating.description = AccountDescription(fixedSpaces)
    }

}
