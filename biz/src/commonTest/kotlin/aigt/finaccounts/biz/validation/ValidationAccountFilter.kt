package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.accountfilter.OwnerIdFilter
import aigt.finaccounts.common.models.accountfilter.SearchStringFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationAccountFilterCorrect(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val searchString = "t t t"
    val ownerIdString = "e9966d0b-bf83-4c61-a86c-de973dfa89d0"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountFilter = AccountFilter(
            SearchStringFilter(searchString),
            OwnerIdFilter(ownerIdString),
        )
    }
    processor.exec(ctx)

    assertEquals(
        0,
        ctx.errors.size,
        message = "${ctx.accountFilter} should not have any errors, but have: ${ctx.errors}",
    )
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(
        searchString,
        ctx.accountFilterValidated.searchString.asString(),
    )
    assertEquals(ownerIdString, ctx.accountFilterValidated.ownerId.asString())
}
