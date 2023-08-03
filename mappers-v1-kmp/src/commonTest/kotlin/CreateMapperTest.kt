package aigt.finaccounts.mappers.kmp.v1

import aigt.finaccounts.api.v1.kmp.models.AccountCreateResponse
import aigt.finaccounts.api.v1.kmp.models.AccountPermissions
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountPermissionClient
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.request.RequestStartTime
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.mappers.kmp.v1.fixture.getAccountCreateRequest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import aigt.finaccounts.common.models.account.AccountStatus as CtxAccountStatus

class CreateMapperTest {
    @Test
    fun fromTransport() {
        val context = FinAccountsContext().apply {
            val request: IRequest = getAccountCreateRequest()
            fromTransport(request)
        }

        assertEquals(
            expected = ContextCommand.CREATE,
            actual = context.command,
            message = "Должен создаваться контекст создания аккаунта",
        )
        assertEquals(
            expected = ContextState.NONE,
            actual = context.state,
            message = "У контекста не должно быть стэйта",
        )
        assertEquals(
            expected = listOf(),
            actual = context.errors,
            message = "У контекста не должно быть стэйта",
        )
        assertEquals(
            expected = ContextWorkMode.STUB,
            actual = context.workMode,
            message = "Должно тестироваться в режиме заглушек",
        )
        assertEquals(
            expected = ContextStubCase.SUCCESS,
            actual = context.stubCase,
            message = "Должна возвращаться заглушка успешного выполнения запроса",
        )
        assertEquals(
            expected = RequestId("75038a32-9d63-4394-968b-d33aaedc057e"),
            actual = context.requestId,
            message = "Должен быть requestId присланный в запросе",
        )
        assertEquals(
            expected = RequestStartTime.NONE,
            actual = context.requestStartTime,
            message = "Время старта указывается бизнес логикой",
        )
        assertEquals(
            expected = AccountFilter.NONE,
            actual = context.accountFilter,
            message = "Фильтр для данной команды не применяется",
        )
        assertEquals(
            expected = AccountId.NONE,
            actual = context.accountRequest.id,
            message = "Id аккаунта должно присваиваться бизнес логикой",
        )
        assertEquals(
            expected = AccountDescription("stub description"),
            actual = context.accountRequest.description,
            message = "Должен быть accountRequest.description присланный в запросе",
        )
        assertEquals(
            expected = AccountOwnerId("9deb6b8c-b797-4b34-9201-776ae1d3cf58"),
            actual = context.accountRequest.ownerId,
            message = "Должен быть accountRequest.ownerId присланный в запросе",
        )
        assertEquals(
            expected = AccountBalance.NONE,
            actual = context.accountRequest.balance,
            message = "Баланс аккаунта должен присваиваться бизнес логикой",
        )
        assertEquals(
            expected = AccountCurrency("RUB"),
            actual = context.accountRequest.currency,
            message = "Должен быть accountRequest.currency присланный в запросе",
        )
        assertEquals(
            expected = CtxAccountStatus.NONE,
            actual = context.accountRequest.status,
            message = "accountRequest.status должен присваиваться бизнес логикой",
        )
        assertEquals(
            expected = AccountLastTransactionTime.NONE,
            actual = context.accountRequest.lastTransactionTime,
            message = "accountRequest.lastTransactionTime должен присваиваться бизнес логикой",
        )
        assertEquals(
            expected = setOf(),
            actual = context.accountRequest.permissionsClient,
            message = "accountRequest.permissionsClient должен присваиваться бизнес логикой",
        )
        assertEquals(
            expected = Transaction.NONE,
            actual = context.transactionRequest,
            message = "Транзакции для данной команды не применяется",
        )
        assertEquals(
            expected = Account.NONE,
            actual = context.accountResponse,
            message = "accountResponse должен присваиваться бизнес логикой",
        )
        assertEquals(
            expected = listOf(),
            actual = context.accountsResponse,
            message = "accountsResponse должен присваиваться бизнес логикой",
        )
        assertEquals(
            expected = listOf(),
            actual = context.historyResponse,
            message = "historyResponse должен присваиваться бизнес логикой",
        )
    }

    @Test
    fun toTransport() {
        val transactionTime = AccountLastTransactionTime(Clock.System.now())
        val context = FinAccountsContext(
            requestId = RequestId("1234"),
            command = ContextCommand.CREATE,
            accountResponse = Account(
                id = AccountId("94852616476317587179"),
                description = AccountDescription("desc"),
                ownerId = AccountOwnerId("cd565097-4b69-490e-b167-b59128475562"),
                balance = AccountBalance(154),
                currency = AccountCurrency("RUB"),
                status = CtxAccountStatus.ACTIVE,
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
            "cd565097-4b69-490e-b167-b59128475562",
            response.account?.ownerId,
        )
        assertEquals(154, response.account?.balance)
        assertEquals("RUB", response.account?.currency)
        assertEquals(AccountStatus.ACTIVE, response.account?.status)
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
