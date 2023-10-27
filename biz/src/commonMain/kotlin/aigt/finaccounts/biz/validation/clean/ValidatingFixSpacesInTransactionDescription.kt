package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Очистка повторяющихся пробелов и замена пробельных символов на стандартный
 * в описании валидируемоой транзакции
 */
fun ICorChainDsl<FinAccountsContext>.validatingFixSpacesInTransactionDescription(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка что описание не пустое
    on { transactionValidating.description != TransactionDescription.NONE }

    // Очистка
    handle {
        val fixedSpaces = transactionValidating.description.asString()
            .replace(Regex("\\p{Space}+"), " ")
        transactionValidating.description = TransactionDescription(fixedSpaces)
    }

}
