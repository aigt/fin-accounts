package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.TransactionId
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionIdCleaned(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val transactionIdString = "e9966d0b-bf83-4c61-a86c-de973dfa89d0"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                id = TransactionId(transactionIdString)
            }
    }
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(TransactionId.NONE, ctx.transactionValidated.id)
}
