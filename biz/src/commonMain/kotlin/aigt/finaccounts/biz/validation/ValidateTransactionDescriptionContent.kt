package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Валидация содержания описания
 */
fun ICorChainDsl<FinAccountsContext>.validateTransactionDescriptionContent(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка контента на валидность
    val regExp = Regex("^[\\p{L}\\d_\\-., ]+$")
    on {
        transactionValidating.description != TransactionDescription.NONE &&
            !transactionValidating.description.matches(regExp)
    }

    // Информирование о проблеме с контентом
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "badContent",
                description = "field must contain letters or be empty",
            ),
        )
    }

}
