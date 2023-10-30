package aigt.finaccounts.biz.fixture

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.request.RequestStartTime
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import kotlinx.datetime.toInstant

val transactionTime =
    AccountLastTransactionTime("2023-08-04T18:43:00.123456789Z".toInstant())
val requestStartTime =
    RequestStartTime("2023-08-04T18:43:00.123456789Z".toInstant())


fun getBaseStubFinAccountsContext() = FinAccountsContext(
    command = ContextCommand.NONE,
    state = ContextState.NONE,
    errors = mutableListOf(),
    workMode = ContextWorkMode.STUB,
    stubCase = ContextStubCase.SUCCESS,
    requestId = RequestId("75038a32-9d63-4394-968b-d33aaedc057e"),
    requestStartTime = requestStartTime,
    accountFilter = AccountFilter.NONE,
    accountRequest = Account.NONE,
    transactionRequest = Transaction.NONE,
    accountResponse = Account.NONE,
    accountsResponse = mutableListOf(),
    historyResponse = mutableListOf(),
)

fun getBaseTestFinAccountsContext(command: ContextCommand) =
    FinAccountsContext(
        command = command,
        state = ContextState.NONE,
        errors = mutableListOf(),
        workMode = ContextWorkMode.TEST,
        stubCase = ContextStubCase.NONE,
        requestId = RequestId("75038a32-9d63-4394-968b-d33aaedc057e"),
        requestStartTime = requestStartTime,
        accountFilter = AccountFilter.NONE,
        accountRequest = Account.NONE,
        transactionRequest = Transaction.NONE,
        accountResponse = Account.NONE,
        accountsResponse = mutableListOf(),
        historyResponse = mutableListOf(),
    )
