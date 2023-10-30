package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.TransactionTimestamp
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionTimestampCleaned(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val transactionTimestamp = Instant.parse("2010-11-01T18:43:00.123456781Z")
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                timestamp = TransactionTimestamp(transactionTimestamp)
            }
    }
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(TransactionTimestamp.NONE, ctx.transactionValidated.timestamp)
}
