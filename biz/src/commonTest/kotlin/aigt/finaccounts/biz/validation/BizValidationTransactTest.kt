package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.models.command.ContextCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationTransactTest {

    private val command = ContextCommand.READ
    private val processor by lazy { AccountProcessor() }

    /*
    Тест валидации очистки полей
    */

    @Test
    fun cleanedDescription() =
        validationDescriptionCleaned(command, processor)

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

