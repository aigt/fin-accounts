package aigt.finaccounts.mappers.kmp.v1

import aigt.finaccounts.api.v1.kmp.models.AccountPermissions
import aigt.finaccounts.api.v1.kmp.models.AccountSearchResponse
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.models.ResponseResult
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.accountfilter.OwnerIdFilter
import aigt.finaccounts.common.models.accountfilter.SearchStringFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.request.RequestStartTime
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.mappers.kmp.v1.fixture.getAccountSearchRequest
import aigt.finaccounts.mappers.kmp.v1.fixture.getSearchFinAccountsContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SearchMapperTest {

    @Test
    fun fromTransport() {
        val context = FinAccountsContext().apply {
            val request: IRequest = getAccountSearchRequest()
            fromTransport(request)
        }

        assertEquals(
            expected = ContextCommand.SEARCH,
            actual = context.command,
            message = "Должен создаваться контекст создания аккаунта",
        )
        assertEquals(
            expected = ContextState.NONE,
            actual = context.state,
            message = "state в запросе не приходит",
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
            expected = AccountFilter(
                searchString = SearchStringFilter(id = "stub search string"),
                ownerId = OwnerIdFilter(id = "cd565097-4b69-490e-b167-b59128475562"),
            ),
            actual = context.accountFilter,
            message = "accountFilter для данной команды не применяется",
        )
        assertEquals(
            expected = Account.NONE,
            actual = context.accountRequest,
            message = "accountRequest для данной команды не применяется",
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
        val context = getSearchFinAccountsContext()

        val response = context.toTransportResponse() as AccountSearchResponse

        assertEquals(
            expected = "search",
            actual = response.responseType,
            message = "Должен отдаватся тип ответа истории аккаунта",
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
            actual = response.accounts,
            message = "accounts не должен быть null, а равен указанному в контексте",
        )
        assertEquals(
            expected = 5,
            actual = response.accounts?.size,
            message = "accounts.size должен быть равен количеству транзакций в контексте",
        )
        response.accounts!!.let { accounts ->
            accounts.first().let { account ->
                assertEquals(
                    expected = "desc hello",
                    actual = account.description,
                    message = "account.description должен быть равен указанному в контексте",
                )
                assertEquals(
                    expected = "32fffd3c-210f-4291-b132-c1631ecb5ec3",
                    actual = account.ownerId,
                    message = "account.ownerId должен быть равен указанному в контексте",
                )
                assertEquals(
                    expected = "RUB",
                    actual = account.currency,
                    message = "account.currency должен быть равен указанному в контексте",
                )
                assertEquals(
                    expected = "10002000300040005001",
                    actual = account.id,
                    message = "account.id должен быть равен указанному в контексте",
                )
                /*assertEquals(
                    expected = "",
                    actual = account.lock,
                    message = "account.lock должен быть равен указанному в контексте",
                )*/
                assertEquals(
                    expected = "2023-08-04T09:34:31.870508306Z",
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
}
