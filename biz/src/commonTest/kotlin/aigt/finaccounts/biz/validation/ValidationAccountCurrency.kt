package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationCurrencyCorrect(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val currencyString = "CNY"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            currency = AccountCurrency(currencyString)
        }
    }
    processor.exec(ctx)

    assertEquals(
        0,
        ctx.errors.size,
        message = "${ctx.accountValidating.currency.asString()} should not have any errors, but have: ${ctx.errors}",
    )
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(currencyString, ctx.accountValidated.currency.asString())
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationCurrencyEmptyError(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            currency = AccountCurrency.NONE
        }
    }
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContextState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("currency", error?.field)
    assertContains(error?.message ?: "", "currency")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationCurrencyCleaned(
    command: ContextCommand,
    processor: AccountProcessor,
    withTransaction: Boolean = false,
) = runTest {
    val currencyString = "CNY"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            currency = AccountCurrency(currencyString)
        }
        if (withTransaction) {
            transactionRequest =
                TransactionStub.getTransactActionTransactionStub()
        }
    }
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(AccountCurrency.NONE, ctx.accountValidated.currency)
}
