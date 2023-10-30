package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.models.command.ContextCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = ContextCommand.SEARCH
    private val processor by lazy { AccountProcessor() }

    @Test
    fun correctAccountFilter() =
        validationAccountFilterCorrect(command, processor)

    @Test
    fun correctAccountFilterOwnerId() =
        validationAccountFilterOwnerIdCorrect(command, processor)

    @Test
    fun badAccountFilterOwnerIdUUIDError() =
        validationAccountFilterOwnerIdUUIDError(command, processor)

    @Test
    fun correctAccountFilterSearchString() =
        validationAccountFilterSearchStringCorrect(command, processor)

    @Test
    fun badAccountFilterSearchStringContentError() =
        validationAccountFilterSearchStringContentError(command, processor)
}

