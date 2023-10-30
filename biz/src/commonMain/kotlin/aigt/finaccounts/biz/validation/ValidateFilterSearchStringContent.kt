package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.accountfilter.SearchStringFilter
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Валидация содержания описания
 */
fun ICorChainDsl<FinAccountsContext>.validateFilterSearchStringContent(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка контента на валидность
    val regExp = Regex("^[\\p{L}\\d_\\-., ]+$")
    on {
        accountFilterValidating.searchString != SearchStringFilter.NONE &&
            !accountFilterValidating.searchString.matches(regExp)
    }

    // Информирование о проблеме с контентом
    handle {
        fail(
            errorValidation(
                field = "filterSearchString",
                violationCode = "badContent",
                description = "field must contain letters or be empty",
            ),
        )
    }

}
