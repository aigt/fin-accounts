package aigt.finaccounts.biz.validation.clean

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Валидация содержания идентификатора счёта
 */
fun ICorChainDsl<FinAccountsContext>.validateIdContent(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка контента на валидность
    val regExp = Regex("^[0-9]{20}$")
    on {
        accountValidating.id != AccountId.NONE &&
            !accountValidating.id.matches(regExp)
    }

    // Информирование о проблеме с контентом
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "badContent",
                description = "field must contain 20 digits",
            ),
        )
    }

}
