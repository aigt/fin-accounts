package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Валидация содержания идентификатора счёта ответного контрагента по операции
 */
fun ICorChainDsl<FinAccountsContext>.validateTransactionCounterpartyContent(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка контента на валидность
    val regExp = Regex("^[0-9]{20}$")
    on {
        transactionValidating.counterparty != TransactionCounterparty.NONE &&
            !transactionValidating.counterparty.matches(regExp)
    }

    // Информирование о проблеме с контентом
    handle {
        fail(
            errorValidation(
                field = "transactionCounterparty",
                violationCode = "badContent",
                description = "field must contain 20 digits or be empty",
            ),
        )
    }

}
