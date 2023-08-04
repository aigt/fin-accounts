package aigt.finaccounts.mappers.kmp.v1

import aigt.finaccounts.api.v1.kmp.models.AccountPermissions
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateResponse
import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.models.ResponseResult
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.request.RequestStartTime
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.mappers.kmp.v1.fixture.getAccountUpdateRequest
import aigt.finaccounts.mappers.kmp.v1.fixture.getUpdateFinAccountsContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import aigt.finaccounts.common.models.account.AccountStatus as CtxAccountStatus

class UpdateMapperTest {

    @Test
    fun fromTransport() {
        val context = FinAccountsContext().apply {
            val request: IRequest = getAccountUpdateRequest()
            fromTransport(request)
        }

        assertEquals(
            expected = ContextCommand.UPDATE,
            actual = context.command,
            message = "Должен создаваться контекст обновления аккаунта",
        )
        assertEquals(
            expected = ContextState.NONE,
            actual = context.state,
            message = "У контекста не должно быть стэйта",
        )
        assertEquals(
            expected = listOf(),
            actual = context.errors,
            message = "У контекста не должно быть ошибок после создания",
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
            expected = AccountId("26c45c31-857f-4d5d-bf59-890817c9320b"),
            actual = context.accountRequest.id,
            message = "Id аккаунта присланный в запросе",
        )
        assertEquals(
            expected = AccountDescription("stub description"),
            actual = context.accountRequest.description,
            message = "accountRequest.description присланный в запросе",
        )
        assertEquals(
            expected = AccountOwnerId("cd565097-4b69-490e-b167-b59128475562"),
            actual = context.accountRequest.ownerId,
            message = "accountRequest.ownerId присланный в запросе",
        )
        assertEquals(
            expected = AccountBalance(1005_00),
            actual = context.accountRequest.balance,
            message = "Баланс аккаунта присланный в запросе",
        )
        assertEquals(
            expected = AccountCurrency("RUB"),
            actual = context.accountRequest.currency,
            message = "accountRequest.currency присланный в запросе",
        )
        assertEquals(
            expected = CtxAccountStatus.FROZEN,
            actual = context.accountRequest.status,
            message = "accountRequest.status присланный в запросе",
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
        val context = getUpdateFinAccountsContext()

        val response = context.toTransportResponse() as AccountUpdateResponse

        assertEquals(
            expected = "update",
            actual = response.responseType,
            message = "Должен отдаватся тип ответа создания аккаунта",
        )
        assertEquals(
            expected = "75038a32-9d63-4394-968b-d33aaedc057e",
            actual = response.requestId,
            message = "requestId должен быть равен указанному в контексте",
        )
        assertEquals(
            expected = ResponseResult.SUCCESS,
            actual = response.result,
            message = "result должен быть равен указанному в контексте",
        )
        assertEquals(
            expected = null,
            actual = response.errors,
            message = "errors должен быть равен указанному в контексте",
        )

        assertNotNull(
            actual = response.account,
            message = "account не должен быть null, а равен указанному в контексте",
        )
        response.account!!.let { account ->
            assertEquals(
                expected = "Простой аккаунт",
                actual = account.description,
                message = "account.description должен быть равен указанному в контексте",
            )
            assertEquals(
                expected = "9deb6b8c-b797-4b34-9201-776ae1d3cf58",
                actual = account.ownerId,
                message = "account.ownerId должен быть равен указанному в контексте",
            )
            assertEquals(
                expected = "RUB",
                actual = account.currency,
                message = "account.currency должен быть равен указанному в контексте",
            )
            assertEquals(
                expected = "10002000300040005000",
                actual = account.id,
                message = "account.id должен быть равен указанному в контексте",
            )
            /*assertEquals(
                expected = "",
                actual = account.lock,
                message = "account.lock должен быть равен указанному в контексте",
            )*/
            assertEquals(
                expected = "2023-08-04T18:43:00.123456789Z",
                actual = account.lastTransaction,
                message = "account.lastTransaction должен быть равен указанному в контексте",
            )
            assertEquals(
                expected = setOf(AccountPermissions.READ),
                actual = account.permissions,
                message = "account.permissions должен быть равен указанному в контексте",
            )
            assertEquals(
                expected = 154,
                actual = account.balance,
                message = "account.balance должен быть равен указанному в контексте",
            )
            assertEquals(
                expected = AccountStatus.ACTIVE,
                actual = account.status,
                message = "account.status должен быть равен указанному в контексте",
            )
        }
    }
}
