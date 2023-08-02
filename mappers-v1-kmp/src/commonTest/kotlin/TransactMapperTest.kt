package aigt.finaccounts.mappers.kmp.v1

import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.kmp.models.AccountTransactObject
import aigt.finaccounts.api.v1.kmp.models.AccountTransactRequest
import aigt.finaccounts.api.v1.kmp.models.AccountTransactResponse
import aigt.finaccounts.api.v1.kmp.models.AccountTransaction
import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.models.TransactionType
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountPermissionClient
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactMapperTest {
    @Test
    fun fromTransport() {
        val request: IRequest = AccountTransactRequest(
            requestType = "transact",
            requestId = "123",
            debug = AccountDebug(
                mode = AccountRequestDebugMode.STUB,
                stub = AccountRequestDebugStubs.SUCCESS,
            ),
            account = AccountTransactObject(
                id = "26c45c31-857f-4d5d-bf59-890817c9320b",
                lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
            ),
            transaction = AccountTransaction(
                type = TransactionType.WITHDRAW,
                amount = 1005_00,
                counterparty = "11102220333044405550",
                description = "stub transaction description",
                timestamp = "2023-07-04T18:43:00.123456789Z",
            ),
        )

//             requestType = "create",
//             requestId = "123",
//             debug = AccountDebug(
//                 mode = AccountRequestDebugMode.STUB,
//                 stub = AccountRequestDebugStubs.SUCCESS,
//             ),
//             account = AccountCreateObject(
//                 ownerId = "cd565097-4b69-490e-b167-b59128475562",
//                 description = "stub",
//                 currency = "RUB",
//             ),
//         )

        val context = FinAccountsContext()
        context.fromTransport(request)

        assertEquals(
            ContextStubCase.SUCCESS,
            context.stubCase,
        )
        assertEquals(
            ContextCommand.TRANSACT,
            context.command,
        )
        assertEquals(
            ContextWorkMode.STUB,
            context.workMode,
        )
    }

    @Test
    fun toTransport() {
        val transactionTime = AccountLastTransactionTime(Clock.System.now())
        val context = FinAccountsContext(
            requestId = RequestId("1234"),
            command = ContextCommand.TRANSACT,
            accountResponse = Account(
                id = AccountId("94852616476317587179"),
                description = AccountDescription("desc"),
                ownerId = AccountOwnerId("cd565097-4b69-490e-b167-b59128475562"),
                balance = AccountBalance(154),
                currency = AccountCurrency("RUB"),
                status = aigt.finaccounts.common.models.account.AccountStatus.ACTIVE,
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

        val response = context.toTransportResponse() as AccountTransactResponse

        assertEquals("transact", response.responseType)
    }
}
