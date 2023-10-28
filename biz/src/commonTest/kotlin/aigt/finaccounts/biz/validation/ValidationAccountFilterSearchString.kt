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
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationAccountFilterSearchStringCorrect(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val searchString = "t t t"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountFilter = AccountFilter(
            SearchStringFilter(searchString),
            OwnerIdFilter.NONE,
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
    assertEquals(OwnerIdFilter.NONE, ctx.accountFilterValidated.ownerId)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationAccountFilterSearchStringContentError(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val searchString = "!@#$%^&*(),{}"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountFilter = AccountFilter(
            SearchStringFilter(searchString),
            OwnerIdFilter.NONE,
        )
    }
    processor.exec(ctx)
    assertEquals(
        1,
        ctx.errors.size,
        message = "${ctx.accountFilter} should not have 1 error, but have: ${ctx.errors}",
    )
    assertEquals(ContextState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("filterSearchString", error?.field)
    assertContains(error?.message ?: "", "filterSearchString")
}
