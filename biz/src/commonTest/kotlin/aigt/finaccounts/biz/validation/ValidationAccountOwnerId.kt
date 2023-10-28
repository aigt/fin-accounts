package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.stubs.SimpleAccountsStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
fun validationOwnerIdCorrect(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val ownerIdString = "e9966d0b-bf83-4c61-a86c-de973dfa89db"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            ownerId = AccountOwnerId(ownerIdString)
        }
    }
    processor.exec(ctx)

    assertEquals(
        0,
        ctx.errors.size,
        message = "${ctx.accountValidating.ownerId.asString()} should not have any errors, but have: ${ctx.errors}",
    )
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(ownerIdString, ctx.accountValidated.ownerId.asString())
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOwnerIdEmptyError(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            ownerId = AccountOwnerId.NONE
        }
    }
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContextState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("ownerId", error?.field)
    assertContains(error?.message ?: "", "ownerId")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOwnerIdUUIDError(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val ownerIdString = "e9966d0b-bf83-4c61-a86c-de973dfa89dk"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            ownerId = AccountOwnerId(ownerIdString)
        }
    }
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContextState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("ownerId", error?.field)
    assertContains(error?.message ?: "", "ownerId")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationOwnerIdCleaned(
    command: ContextCommand,
    processor: AccountProcessor,
) = runTest {
    val ownerIdString = "e9966d0b-bf83-4c61-a86c-de973dfa89d0"
    val ctx = getBaseTestFinAccountsContext(command).apply {
        accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
            ownerId = AccountOwnerId(ownerIdString)
        }
    }
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContextState.FAILING, ctx.state)
    assertEquals(AccountOwnerId.NONE, ctx.accountValidated.ownerId)
}

