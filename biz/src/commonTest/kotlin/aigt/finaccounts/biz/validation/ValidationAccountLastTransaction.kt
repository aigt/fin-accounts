package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationLastTransactionCleaned(
    command: ContextCommand,
    processor: AccountProcessor,
    withTransaction: Boolean = false,
) = runTest {
    val timestampString = "2023-06-26T18:43:00.123456000Z"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            lastTransactionTime = AccountLastTransactionTime(
                timestamp = Instant.parse(timestampString),
            )
        }
        if (withTransaction) {
            transactionRequest =
                TransactionStub.getTransactActionTransactionStub()
        }
    }
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(
        AccountLastTransactionTime.NONE,
        ctx.accountValidated.lastTransactionTime,
    )
}
