package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.common.models.command.ContextCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = ContextCommand.SEARCH
    private val processor by lazy { AccountProcessor() }

    /*@Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)*/

    /*@Test
    fun cleanedDescription() = validationDescriptionCleaned(command, processor)*/
}

