package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

/**
 * Валидация содержания идентификатора владельца
 */
fun ICorChainDsl<FinAccountsContext>.validateOwnerIdContent(
    title: String,
) = worker {

    // Название воркера
    this.title = title

    // Проверка контента на валидность
    val uuidPattern =
        "^\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}$"
    val regExp = Regex(uuidPattern)
    on {
        accountValidating.ownerId != AccountOwnerId.NONE &&
            !accountValidating.ownerId.matches(regExp)
    }

    // Информирование о проблеме с контентом
    handle {
        fail(
            errorValidation(
                field = "ownerId",
                violationCode = "badContent",
                description = "field must contain uuid or be empty",
            ),
        )
    }

}
