package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.helpers.errorValidation
import aigt.finaccounts.common.helpers.fail
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
    val regExp =
        Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
    on { !accountValidating.ownerId.matches(regExp) }

    // Информирование о проблеме с контентом
    handle {
        fail(
            errorValidation(
                field = "ownerId",
                violationCode = "badContent",
                description = "field must contain letters or be empty",
            ),
        )
    }

}