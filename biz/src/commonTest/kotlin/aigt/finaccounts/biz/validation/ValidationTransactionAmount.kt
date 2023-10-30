package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.common.models.transaction.TransactionId
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionAmountEmptyError(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                amount = TransactionAmount.NONE
            }
    }
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContextState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("transactionAmount", error?.field)
    assertContains(error?.message ?: "", "transactionAmount")
}
