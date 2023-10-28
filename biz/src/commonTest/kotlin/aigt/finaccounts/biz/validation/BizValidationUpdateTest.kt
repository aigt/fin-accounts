package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.models.command.ContextCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = ContextCommand.UPDATE
    private val processor by lazy { AccountProcessor() }

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

    /*
    Тест валидации поля: description
    */

    @Test
    fun correctDescription() = validationDescriptionCorrect(command, processor)

    @Test
    fun trimDescription() = validationDescriptionTrim(command, processor)

    @Test
    fun fixSpacesDescription() =
        validationDescriptionFixSpaces(command, processor)

    @Test
    fun badSymbolsDescription() =
        validationDescriptionSymbolsError(command, processor)

    /*
    Тест валидации поля: ownerId
     */

    @Test
    fun correctOwnerId() =
        validationOwnerIdCorrect(command, processor)

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

