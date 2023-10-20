package aigt.finaccounts.logging.v1.mapper.fixture

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


fun getBaseFinAccountsContext() = FinAccountsContext(
    command = ContextCommand.NONE,
    state = ContextState.FINISHING,
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


fun getCreateFinAccountsContext() = getBaseFinAccountsContext().copy(
    command = ContextCommand.CREATE,
    accountResponse = Account(
        id = AccountId("10002000300040005000"),
        description = AccountDescription("Простой аккаунт"),
        ownerId = AccountOwnerId("9deb6b8c-b797-4b34-9201-776ae1d3cf58"),
        balance = AccountBalance(154),
        currency = AccountCurrency("RUB"),
        status = AccountStatus.ACTIVE,
        lastTransactionTime = transactionTime,
        permissionsClient = mutableSetOf(
            AccountPermissionClient.READ,
        ),
    ),
)

