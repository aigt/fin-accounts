package aigt.finaccounts.mappers.kmp.v1

import aigt.finaccounts.api.v1.kmp.models.AccountHistoryResponse
import aigt.finaccounts.api.v1.kmp.models.AccountPermissions
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.models.ResponseResult
import aigt.finaccounts.api.v1.kmp.models.TransactionType
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
import aigt.finaccounts.mappers.kmp.v1.fixture.getAccountHistoryRequest
import aigt.finaccounts.mappers.kmp.v1.fixture.getHistoryFinAccountsContext
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import aigt.finaccounts.common.models.account.AccountStatus as CtxAccountStatus

class HistoryMapperTest {

    @Test
    fun fromTransport() {
        val context = FinAccountsContext().apply {
            val request: IRequest = getAccountHistoryRequest()
            fromTransport(request)
        }

        assertEquals(
            expected = ContextCommand.HISTORY,
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
            expected = AccountFilter.NONE,
            actual = context.accountFilter,
            message = "accountFilter для данной команды не применяется",
        )
        assertEquals(
            expected = AccountId("26c45c31-857f-4d5d-bf59-890817c9320b"),
            actual = context.accountRequest.id,
            message = "accountRequest.id должен быть присланный в запросе",
        )
        assertEquals(
            expected = AccountDescription.NONE,
            actual = context.accountRequest.description,
            message = "accountRequest.description в запросе не приходит",
        )
        assertEquals(
            expected = AccountOwnerId.NONE,
            actual = context.accountRequest.ownerId,
            message = "accountRequest.ownerId в запросе не приходит",
        )
        assertEquals(
            expected = AccountBalance.NONE,
            actual = context.accountRequest.balance,
            message = "accountRequest.balance в запросе не приходит",
        )
        assertEquals(
            expected = AccountCurrency.NONE,
            actual = context.accountRequest.currency,
            message = "accountRequest.currency в запросе не приходит",
        )
        assertEquals(
            expected = CtxAccountStatus.NONE,
            actual = context.accountRequest.status,
            message = "accountRequest.status в запросе не приходит",
        )
        assertEquals(
            expected = AccountLastTransactionTime.NONE,
            actual = context.accountRequest.lastTransactionTime,
            message = "accountRequest.lastTransactionTime в запросе не приходит",
        )
        assertEquals(
            expected = setOf(),
            actual = context.accountRequest.permissionsClient,
            message = "accountRequest.permissionsClient в запросе не приходит",
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
        val context = getHistoryFinAccountsContext()

        val response = context.toTransportResponse() as AccountHistoryResponse

        assertEquals(
            expected = "history",
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
                expected = 1200_00,
                actual = account.balance,
                message = "account.balance должен быть равен указанному в контексте",
            )
            assertEquals(
                expected = AccountStatus.ACTIVE,
                actual = account.status,
                message = "account.status должен быть равен указанному в контексте",
            )
        }

        assertNotNull(
            actual = response.history,
            message = "history не должен быть null, а равен указанному в контексте",
        )
        assertEquals(
            expected = 5,
            actual = response.history?.size,
            message = "history.size должен быть равен количеству транзакций в контексте",
        )
        response.history!!.let { history ->
            history.first().let { transaction ->
                assertEquals(
                    expected = TransactionType.INCOME,
                    actual = transaction.type,
                    message = "history.firstOrNull().type должен быть равен указанному в контексте",
                )
                assertEquals(
                    expected = "99992000300040005000",
                    actual = transaction.counterparty,
                    message = "history.firstOrNull().counterparty должен быть равен указанному в контексте",
                )
                transaction.description?.let { description ->
                    assertContains(
                        charSequence = description,
                        other = "filter stub",
                        message = "history.firstOrNull().description должен быть операция с указанным описанием",
                    )
                }
            }

            assertEquals(
                expected = 500_00,
                actual = history.firstOrNull { it.description!!.contains("index: 4 ") }?.amount,
                message = "history.firstOrNull().amount должен быть операция с суммой как в контексте",
            )
        }
    }
}
