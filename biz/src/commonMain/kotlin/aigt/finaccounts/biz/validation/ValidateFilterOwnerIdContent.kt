package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.accountfilter.OwnerIdFilter
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Валидация содержания поиска по идентификатору владельца
 */
fun ICorChainDsl<FinAccountsContext>.validateFilterOwnerIdContent(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка контента на валидность
    val uuidPattern =
        "^\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}$"
    val regExp = Regex(uuidPattern)
    on {
        accountFilterValidating.ownerId != OwnerIdFilter.NONE &&
            !accountFilterValidating.ownerId.matches(regExp)
    }

    // Информирование о проблеме с контентом
    handle {
        fail(
            errorValidation(
                field = "filterOwnerId",
                violationCode = "badContent",
                description = "field must contain uuid or be empty",
            ),
        )
    }

}
