package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.models.command.ContextCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationHistoryTest {

    private val command = ContextCommand.HISTORY
    private val processor by lazy { AccountProcessor() }

    /*
    Тест валидации очистки полей
    */

    @Test
    fun cleanedDescription() =
        validationDescriptionCleaned(command, processor)

    @Test
    fun cleanedOwnerId() =
        validationOwnerIdCleaned(command, processor)

    @Test
    fun cleanedBalance() =
        validationBalanceCleaned(command, processor)

    @Test
    fun cleanedCurrency() =
        validationCurrencyCleaned(command, processor)

    @Test
    fun cleanedStatus() =
        validationStatusCleaned(command, processor)

    @Test
    fun cleanedLastTransaction() =
        validationLastTransactionCleaned(command, processor)

    @Test
    fun cleanedPermissionClient() =
        validationPermissionsClientCleaned(command, processor)

    /*
    Тест валидации поля: id
    */

    @Test
    fun correctId() =
        validationIdCorrect(command, processor)

    @Test
    fun emptyIdError() =
        validationIdEmptyError(command, processor)

    @Test
    fun badIdContentError() =
        validationIdContentError(command, processor)
}

