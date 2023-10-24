package aigt.finaccounts.biz.stub

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseStubFinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.accountfilter.OwnerIdFilter
import aigt.finaccounts.common.models.accountfilter.SearchStringFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.stubs.AccountStub
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.toInstant
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

// @OptIn(ExperimentalCoroutinesApi::class)
class AccountSearchStubTest {

    private val accountProcessor = AccountProcessor()

    private val searchString = "для теста 111"
    private val ownerIdString = "cd565097-4b69-490e-b167-b59128475562"

    private fun getFinAccountsContext() = getBaseStubFinAccountsContext().copy(
        command = ContextCommand.SEARCH,
        accountFilter = AccountFilter(
            SearchStringFilter(searchString),
            OwnerIdFilter(ownerIdString),
        ),
    )

    @Test
    fun search() = runTest {
        val ctx = getFinAccountsContext()

        accountProcessor.exec(ctx)

        assertEquals(
            Account.NONE,
            ctx.accountResponse,
        )
        assertContentEquals(
            AccountStub.prepareAccountsList(
                listSize = 10,
                currency = AccountCurrency(code = "XYZ"),
                balance = AccountBalance(cents = 100),
                filter = AccountFilter(
                    SearchStringFilter(searchString),
                    OwnerIdFilter(ownerIdString),
                ),
                lastTransactionTime = AccountLastTransactionTime("2023-10-23T08:33:23.842813418Z".toInstant()),
            ),
            ctx.accountsResponse,
        )
    }

    @Test
    fun badId() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_ID
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badSearchStringFilter() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_SEARCH_STRING_FILTER
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("search-string-filter", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badOwnerIdFilter() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_OWNER_ID_FILTER
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("owner-id-filter", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.DB_ERROR
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun noStubCase() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.NONE
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("stub-case", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}

