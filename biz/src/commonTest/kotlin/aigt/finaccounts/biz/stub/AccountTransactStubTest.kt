package aigt.finaccounts.biz.stub

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseStubFinAccountsContext
import aigt.finaccounts.common.helpers.makeTransactRequestAccount
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

// @OptIn(ExperimentalCoroutinesApi::class)
class AccountTransactStubTest {

    private val accountProcessor = AccountProcessor()

    private val accountId = AccountId("99992000300040005000")

    private fun getFinAccountsContext() = getBaseStubFinAccountsContext().copy(
        command = ContextCommand.TRANSACT,
        accountRequest = makeTransactRequestAccount(accountId),
        transactionRequest = TransactionStub.getTransactActionTransactionStub(),
    )

    @Test
    fun transact() = runTest {
        val ctx = getFinAccountsContext()

        accountProcessor.exec(ctx)

        assertEquals(
            accountId,
            ctx.accountResponse.id,
        )
        assertEquals(
            SimpleAccountsStub.ACCOUNT_DESCRIPTION,
            ctx.accountResponse.description,
        )
        assertEquals(
            SimpleAccountsStub.ACCOUNT_OWNER_ID,
            ctx.accountResponse.ownerId,
        )
        assertEquals(
            SimpleAccountsStub.ACCOUNT_BALANCE,
            ctx.accountResponse.balance,
        )
        assertEquals(
            SimpleAccountsStub.ACCOUNT_CURRENCY,
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
        assertContentEquals(
            listOf(
                TransactionStub.getSimpleTransactionStub(
                    TransactionAccountId(accountId.asString()),
                ),
            ),
            ctx.historyResponse,
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
    fun badTransactionAmount() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_TRANSACTION_AMOUNT
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("transaction-amount", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTransactionCounterparty() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_TRANSACTION_COUNTERPARTY
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals(
            "transaction-counterparty",
            ctx.errors.firstOrNull()?.field,
        )
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTransactionType() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_TRANSACTION_TYPE
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals("transaction-type", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTransactionDescription() = runTest {
        val ctx = getFinAccountsContext().apply {
            stubCase = ContextStubCase.BAD_TRANSACTION_DESCRIPTION
        }

        accountProcessor.exec(ctx)

        assertEquals(Account(), ctx.accountResponse)
        assertEquals(
            "transaction-description",
            ctx.errors.firstOrNull()?.field,
        )
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

