package aigt.finaccounts.biz.stub

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseStubFinAccountsContext
import aigt.finaccounts.common.helpers.makeCreateRequestAccount
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.stubs.SimpleAccountsStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

// @OptIn(ExperimentalCoroutinesApi::class)
class AccountCreateStubTest {

    private val accountProcessor = AccountProcessor()
    private val ACCOUNT_DESCRIPTION = AccountDescription("desc for test biz")
    private val ACCOUNT_OWNER_ID =
        AccountOwnerId("9deb6b8c-b797-4b34-9201-776ae1d31111")
    private val ACCOUNT_CURRENCY = AccountCurrency("EUR")

    private fun getFinAccountsContext() = getBaseStubFinAccountsContext().copy(
        command = ContextCommand.CREATE,
        accountRequest = makeCreateRequestAccount(
            ACCOUNT_DESCRIPTION,
            ACCOUNT_OWNER_ID,
            ACCOUNT_CURRENCY,
        ),
    )

    @Test
    fun create() = runTest {
        val ctx = getFinAccountsContext()

        accountProcessor.exec(ctx)

        assertEquals(
            SimpleAccountsStub.ACCOUNT_ID,
            ctx.accountResponse.id,
        )
        assertEquals(
            ACCOUNT_DESCRIPTION,
            ctx.accountResponse.description,
        )
        assertEquals(
            ACCOUNT_OWNER_ID,
            ctx.accountResponse.ownerId,
        )
        assertEquals(
            SimpleAccountsStub.ACCOUNT_BALANCE,
            ctx.accountResponse.balance,
        )
        assertEquals(
            ACCOUNT_CURRENCY,
            ctx.accountResponse.currency,
        )
        assertEquals(
            SimpleAccountsStub.ACCOUNT_STATUS,
            ctx.accountResponse.status,
        )
        assertEquals(
            SimpleAccountsStub.ACCOUNT_LAST_TRANSACTION_TIME,
            ctx.accountResponse.lastTransactionTime,
        )
        assertEquals(
            SimpleAccountsStub.ACCOUNT_CLIENT_PERMISSIONS,
            ctx.accountResponse.permissionsClient,
        )
    }

    @Test
    fun emptyOwnerId() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.EMPTY_OWNER_ID
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("owner-id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun emptyCurrency() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.EMPTY_CURRENCY
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("currency", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badOwnerId() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_OWNER_ID
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("owner-id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badDescription() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_DESCRIPTION
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
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

