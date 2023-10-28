package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.models.command.ContextCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val command = ContextCommand.CREATE
    private val processor by lazy { AccountProcessor() }

    /*
    Тест валидации поля: description
    */

    @Test
    fun correctDescription() =
        validationDescriptionCorrect(command, processor)

    @Test
    fun trimDescription() =
        validationDescriptionTrim(command, processor)

    @Test
    fun fixSpacesDescription() =
        validationDescriptionFixSpaces(command, processor)

    @Test
    fun badSymbolsDescriptionError() =
        validationDescriptionSymbolsError(command, processor)

    /*
    Тест валидации поля: ownerId
     */

    @Test
    fun correctOwnerId() =
        validationOwnerIdCorrect(command, processor)

    @Test
    fun emptyOwnerIdError() =
        validationOwnerIdEmptyError(command, processor)

    @Test
    fun uuidOwnerIdError() =
        validationOwnerIdUUIDError(command, processor)

    /*
    Тест валидации поля: currency
     */

    @Test
    fun correctCurrency() =
        validationCurrencyCorrect(command, processor)

    @Test
    fun emptyCurrencyError() =
        validationCurrencyEmptyError(command, processor)
}

