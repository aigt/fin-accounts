package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionDescriptionCorrect(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val transactionDescString = "abc ABC абв АБВ .,_-"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                description = TransactionDescription(transactionDescString)
            }
    }
    processor.exec(ctx)
    assertEquals(
        0,
        ctx.errors.size,
        message = "${ctx.transactionValidated.description.asString()} should not have any errors, but have: ${ctx.errors}",
    )
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(
        "abc ABC абв АБВ .,_-",
        ctx.transactionValidated.description.asString(),
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionDescriptionTrim(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val transactionDescString = " \n\tabc \n\t"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest = TransactionStub.getTransactActionTransactionStub()
            .apply {
                description = TransactionDescription(transactionDescString)
            }
    }
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals("abc", ctx.transactionValidated.description.asString())
}


@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionDescriptionFixSpaces(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val transactionDescString = "A  A\tA\nA\u000CA\rA A"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                description = TransactionDescription(transactionDescString)
            }
    }
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(
        "A A A A A A A",
        ctx.transactionValidated.description.asString(),
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTransactionDescriptionSymbolsError(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val transactionDescString = "!@#$%^&*(),{}"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
        transactionRequest =
            TransactionStub.getTransactActionTransactionStub().apply {
                description = TransactionDescription(transactionDescString)
            }
    }
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContextState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

