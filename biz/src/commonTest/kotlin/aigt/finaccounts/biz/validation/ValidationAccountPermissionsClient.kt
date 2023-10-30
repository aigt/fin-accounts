package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.account.AccountPermissionClient
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
fun validationPermissionsClientCleaned(
    command: ContextCommand,
    processor: AccountProcessor,
    withTransaction: Boolean = false,
) = runTest {
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            permissionsClient.clear()
            permissionsClient.add(AccountPermissionClient.READ)
            permissionsClient.add(AccountPermissionClient.HISTORY)
        }
        if (withTransaction) {
            transactionRequest =
                TransactionStub.getTransactActionTransactionStub()
        }
    }
    processor.exec(ctx)
    assertEquals(
        0,
        ctx.errors.size,
        message = "${ctx.accountValidating.permissionsClient} should not have any errors, but have: ${ctx.errors}",
    )
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertTrue(ctx.accountValidated.permissionsClient.isEmpty())
}
