package aigt.finaccounts.mappers.jvm.v1

import aigt.finaccounts.api.v1.jackson.models.AccountCreateObject
import aigt.finaccounts.api.v1.jackson.models.AccountCreateRequest
import aigt.finaccounts.api.v1.jackson.models.AccountCreateResponse
import aigt.finaccounts.api.v1.jackson.models.AccountDebug
import aigt.finaccounts.api.v1.jackson.models.AccountPermissions
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.jackson.models.IRequest
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountPermissionClient
import aigt.finaccounts.common.models.account.AccountStatus
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import kotlinx.datetime.Clock.System.now
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import aigt.finaccounts.api.v1.jackson.models.AccountStatus as AccountCreateObjectStatus
import aigt.finaccounts.api.v1.jackson.models.AccountStatus as TransportAccountStatus

class MapperTest {
    @Test
    fun fromTransport() {
        val request: IRequest = AccountCreateRequest(
            requestType = "create",
            requestId = "123",
            debug = AccountDebug(
                mode = AccountRequestDebugMode.STUB,
                stub = AccountRequestDebugStubs.SUCCESS,
            ),
            account = AccountCreateObject(
                ownerId = UUID.fromString("cd565097-4b69-490e-b167-b59128475562"),
                description = "stub",
                balance = 154,
                currency = "RUB",
                status = AccountCreateObjectStatus.ACTIVE,
            ),
        )
        val context = FinAccountsContext()
        context.fromTransport(request)

        assertEquals(ContextStubCase.SUCCESS, context.stubCase)
        assertEquals(ContextWorkMode.STUB, context.workMode)
        assertEquals(
            AccountOwnerId("cd565097-4b69-490e-b167-b59128475562"),
            context.accountRequest.ownerId,
        )
        assertEquals(
            AccountDescription("stub"),
            context.accountRequest.description,
        )
        assertEquals(AccountBalance(154), context.accountRequest.balance)
        assertEquals(AccountCurrency("RUB"), context.accountRequest.currency)
        assertEquals(AccountStatus.ACTIVE, context.accountRequest.status)
    }

    @Test
    fun toTransport() {
        val transactionTime = AccountLastTransactionTime(now())
        val context = FinAccountsContext(
            requestId = RequestId("1234"),
            command = ContextCommand.CREATE,
            accountResponse = Account(
                id = AccountId("94852616476317587179"),
                description = AccountDescription("desc"),
                ownerId = AccountOwnerId("cd565097-4b69-490e-b167-b59128475562"),
                balance = AccountBalance(154),
                currency = AccountCurrency("RUB"),
                status = AccountStatus.ACTIVE,
                lastTransactionTime = transactionTime,
                permissionsClient = mutableSetOf(
                    AccountPermissionClient.READ,
                    AccountPermissionClient.HISTORY,
                ),
            ),
            errors = mutableListOf(
                ContextError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                ),
            ),
            state = ContextState.RUNNING,
        )

        val response = context.toTransportResponse() as AccountCreateResponse

        assertEquals("1234", response.requestId)
        assertEquals("desc", response.account?.description)
        assertEquals(
            UUID.fromString("cd565097-4b69-490e-b167-b59128475562"),
            response.account?.ownerId,
        )
        assertEquals(154, response.account?.balance)
        assertEquals("RUB", response.account?.currency)
        assertEquals(TransportAccountStatus.ACTIVE, response.account?.status)
        assertEquals(
            transactionTime.asString(),
            response.account?.lastTransaction,
        )
        assertEquals(
            setOf(
                AccountPermissions.READ,
                AccountPermissions.HISTORY,
            ),
            response.account?.permissions,
        )
        assertEquals(1, response.errors?.size)
        assertEquals("err", response.errors?.firstOrNull()?.code)
        assertEquals("request", response.errors?.firstOrNull()?.group)
        assertEquals("title", response.errors?.firstOrNull()?.field)
        assertEquals("wrong title", response.errors?.firstOrNull()?.message)
    }
}
