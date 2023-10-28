package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionCounterpartyCorrect(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val idString = "12344321000055556789"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                counterparty = TransactionCounterparty(idString)
            }
    }
    processor.exec(ctx)

    assertEquals(
        0,
        ctx.errors.size,
        message = "${ctx.transactionValidated.counterparty.asString()} should not have any errors, but have: ${ctx.errors}",
    )
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(idString, ctx.transactionValidated.counterparty.asString())
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionCounterpartyEmptyError(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                counterparty = TransactionCounterparty.NONE
            }
    }
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContextState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("transactionCounterparty", error?.field)
    assertContains(error?.message ?: "", "transactionCounterparty")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionCounterpartyContentError(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val idString = "a1231234123412341234"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                counterparty = TransactionCounterparty(idString)
            }
    }
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContextState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("transactionCounterparty", error?.field)
    assertContains(error?.message ?: "", "transactionCounterparty")
}
