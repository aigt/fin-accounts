package aigt.finaccounts.biz.stub

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseStubFinAccountsContext
import aigt.finaccounts.common.helpers.makeUpdateRequestAccount
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountStatus
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.stubs.SimpleAccountsStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

// @OptIn(ExperimentalCoroutinesApi::class)
class AccountUpdateStubTest {

    private val accountProcessor = AccountProcessor()

    private val accountId = AccountId("99992000300040005000")
    private val accountDescription = AccountDescription("desc for test biz")
    private val accountOwnerId =
        AccountOwnerId("9deb6b8c-b797-4b34-9201-776ae1d31111")
    private val accountCurrency = AccountCurrency("EUR")
    private val accountBalance = AccountBalance(100_101_12)
    private val accountStatus = AccountStatus.CLOSED

    private fun getFinAccountsContext() = getBaseStubFinAccountsContext().copy(
        command = ContextCommand.UPDATE,
        accountRequest = makeUpdateRequestAccount(
            accountId,
            accountDescription,
            accountOwnerId,
            accountCurrency,
            accountBalance,
            accountStatus,
        ),
    )

    @Test
    fun update() = runTest {
        val ctx = getFinAccountsContext()

        accountProcessor.exec(ctx)

        assertEquals(
            accountId,
            ctx.accountResponse.id,
        )
        assertEquals(
            accountDescription,
            ctx.accountResponse.description,
        )
        assertEquals(
            accountOwnerId,
            ctx.accountResponse.ownerId,
        )
        assertEquals(
            accountBalance,
            ctx.accountResponse.balance,
        )
        assertEquals(
            accountCurrency,
            ctx.accountResponse.currency,
        )
        assertEquals(
            accountStatus,
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
    fun emptyId() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.EMPTY_ID
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
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

