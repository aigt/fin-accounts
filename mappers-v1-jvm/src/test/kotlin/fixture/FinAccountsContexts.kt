package aigt.finaccounts.mappers.jvm.v1.fixture

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
import aigt.finaccounts.common.models.accountfilter.OwnerIdFilter
import aigt.finaccounts.common.models.accountfilter.SearchStringFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.request.RequestStartTime
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.common.models.transaction.TransactionType
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.stubs.AccountStub.prepareAccountsList
import aigt.finaccounts.stubs.AccountStub.prepareTransactionsList
import kotlinx.datetime.toInstant

val transactionTime =
    AccountLastTransactionTime("2023-08-04T18:43:00.123456789Z".toInstant())
val requestStartTime =
    RequestStartTime("2023-08-04T18:43:00.123456789Z".toInstant())


fun getBaseFinAccountsContext() = FinAccountsContext(
    command = ContextCommand.NONE,
    state = ContextState.FINISHED,
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


fun getReadFinAccountsContext() = getBaseFinAccountsContext().copy(
    command = ContextCommand.READ,
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


fun getUpdateFinAccountsContext() = getBaseFinAccountsContext().copy(
    command = ContextCommand.UPDATE,
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


fun getSearchFinAccountsContext() = getBaseFinAccountsContext().copy(
    command = ContextCommand.SEARCH,
    accountsResponse = prepareAccountsList(
        listSize = 5,
        currency = AccountCurrency("RUB"),
        balance = AccountBalance(154),
        filter = AccountFilter(
            searchString = SearchStringFilter(id = "hello"),
            ownerId = OwnerIdFilter(id = "32fffd3c-210f-4291-b132-c1631ecb5ec3"),
        ),
        lastTransactionTime = AccountLastTransactionTime("2023-08-04T09:34:31.870508306Z".toInstant()),
    ),
)


fun getHistoryFinAccountsContext() = getBaseFinAccountsContext().copy(
    command = ContextCommand.HISTORY,
    accountResponse = Account(
        id = AccountId("10002000300040005000"),
        description = AccountDescription("Простой аккаунт"),
        ownerId = AccountOwnerId("9deb6b8c-b797-4b34-9201-776ae1d3cf58"),
        balance = AccountBalance(1200_00),
        currency = AccountCurrency("RUB"),
        status = AccountStatus.ACTIVE,
        lastTransactionTime = transactionTime,
        permissionsClient = mutableSetOf(
            AccountPermissionClient.READ,
        ),
    ),
    historyResponse = prepareTransactionsList(
        listSize = 5,
        filter = "filter stub",
        accountId = TransactionAccountId("10002000300040005000"),
        type = TransactionType.INCOME,
    ),
)


fun getTransactFinAccountsContext() = getBaseFinAccountsContext().copy(
    command = ContextCommand.TRANSACT,
    accountResponse = Account(
        id = AccountId("10002000300040005000"),
        description = AccountDescription("Простой аккаунт"),
        ownerId = AccountOwnerId("9deb6b8c-b797-4b34-9201-776ae1d3cf58"),
        balance = AccountBalance(1200_00),
        currency = AccountCurrency("RUB"),
        status = AccountStatus.ACTIVE,
        lastTransactionTime = transactionTime,
        permissionsClient = mutableSetOf(
            AccountPermissionClient.READ,
        ),
    ),
    historyResponse = prepareTransactionsList(
        listSize = 1,
        filter = "filter stub",
        accountId = TransactionAccountId("10002000300040005000"),
        type = TransactionType.WITHDRAW,
    ),
)
