package aigt.finaccounts.mappers.jvm.v1

import aigt.finaccounts.api.v1.jackson.models.*
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.accountfilter.OwnerIdFilter
import aigt.finaccounts.common.models.accountfilter.SearchStringFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.mappers.jvm.v1.exceptions.UnknownRequestClass
import aigt.finaccounts.common.models.account.AccountStatus as ContextAccountStatus
import aigt.finaccounts.common.models.transaction.TransactionType as ContextTransactionType

fun FinAccountsContext.fromTransport(request: IRequest) = when (request) {
    is AccountCreateRequest -> fromTransport(request)
    is AccountReadRequest -> fromTransport(request)
    is AccountUpdateRequest -> fromTransport(request)
    is AccountHistoryRequest -> fromTransport(request)
    is AccountSearchRequest -> fromTransport(request)
    is AccountTransactRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}


private fun String?.toAccountId() = this
    ?.let { AccountId(it) }
    ?: AccountId.NONE

private fun String?.toAccountWithId() = Account(
    id = this.toAccountId(),
)

private fun IRequest?.requestId() =
    this?.requestId?.let { RequestId(it) } ?: RequestId.NONE

private fun AccountDebug?.transportToWorkMode(): ContextWorkMode =
    when (this?.mode) {
        AccountRequestDebugMode.PROD -> ContextWorkMode.PROD
        AccountRequestDebugMode.TEST -> ContextWorkMode.TEST
        AccountRequestDebugMode.STUB -> ContextWorkMode.STUB
        null -> ContextWorkMode.PROD
    }

private fun AccountDebug?.transportToStubCase(): ContextStubCase =
    when (this?.stub) {
        AccountRequestDebugStubs.SUCCESS -> ContextStubCase.SUCCESS
        AccountRequestDebugStubs.NOT_FOUND -> ContextStubCase.NOT_FOUND
        AccountRequestDebugStubs.BAD_ID -> ContextStubCase.BAD_ID
        AccountRequestDebugStubs.BAD_DESCRIPTION -> ContextStubCase.BAD_DESCRIPTION
        AccountRequestDebugStubs.BAD_OWNER_ID -> ContextStubCase.BAD_OWNER_ID
        AccountRequestDebugStubs.BAD_BALANCE -> ContextStubCase.BAD_BALANCE
        AccountRequestDebugStubs.BAD_CURRENCY -> ContextStubCase.BAD_CURRENCY
        AccountRequestDebugStubs.BAD_LAST_TRANSACTION -> ContextStubCase.BAD_LAST_TRANSACTION
        AccountRequestDebugStubs.BAD_STATUS -> ContextStubCase.BAD_STATUS
        AccountRequestDebugStubs.BAD_TRANSACTION_AMOUNT -> ContextStubCase.BAD_TRANSACTION_AMOUNT
        AccountRequestDebugStubs.BAD_TRANSACTION_COUNTERPARTY -> ContextStubCase.BAD_TRANSACTION_COUNTERPARTY
        AccountRequestDebugStubs.BAD_TRANSACTION_TIMESTAMP -> ContextStubCase.BAD_TRANSACTION_TIMESTAMP
        AccountRequestDebugStubs.BAD_TRANSACTION_DESCRIPTION -> ContextStubCase.BAD_TRANSACTION_DESCRIPTION
        AccountRequestDebugStubs.BAD_TRANSACTION_TYPE -> ContextStubCase.BAD_TRANSACTION_TYPE
        AccountRequestDebugStubs.BAD_SEARCH_STRING -> ContextStubCase.BAD_SEARCH_STRING
        AccountRequestDebugStubs.DB_ERROR -> ContextStubCase.DB_ERROR
        null -> ContextStubCase.NONE
    }

fun FinAccountsContext.fromTransport(request: AccountCreateRequest) {
    command = ContextCommand.CREATE
    requestId = request.requestId()
    accountRequest = request.account?.toInternal() ?: Account()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun FinAccountsContext.fromTransport(request: AccountReadRequest) {
    command = ContextCommand.READ
    requestId = request.requestId()
    accountRequest = request.account?.id.toAccountWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun FinAccountsContext.fromTransport(request: AccountUpdateRequest) {
    command = ContextCommand.UPDATE
    requestId = request.requestId()
    accountRequest = request.account?.toInternal() ?: Account()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun FinAccountsContext.fromTransport(request: AccountHistoryRequest) {
    command = ContextCommand.HISTORY
    requestId = request.requestId()
    accountRequest = request.account?.id.toAccountWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun FinAccountsContext.fromTransport(request: AccountSearchRequest) {
    command = ContextCommand.SEARCH
    requestId = request.requestId()
    accountFilter = request.accountFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun FinAccountsContext.fromTransport(request: AccountTransactRequest) {
    command = ContextCommand.TRANSACT
    requestId = request.requestId()
    accountRequest = request.account?.toInternal() ?: Account()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    transactionRequest = request.transaction?.toInternal() ?: Transaction()
}

private fun AccountSearchFilter?.toInternal(): AccountFilter = AccountFilter(
    searchString = this?.searchString
        ?.let { SearchStringFilter(it) }
        ?: SearchStringFilter.NONE,
    ownerId = this?.ownerId
        ?.let { OwnerIdFilter(it.toString()) }
        ?: OwnerIdFilter.NONE,
)

private fun AccountCreateObject.toInternal(): Account = Account(
    description = this.description
        ?.let { AccountDescription(it) }
        ?: AccountDescription.NONE,
    ownerId = this.ownerId
        ?.let { AccountOwnerId(it.toString()) }
        ?: AccountOwnerId.NONE,
    balance = AccountBalance.NONE,
    currency = this.currency
        ?.let { AccountCurrency(it) }
        ?: AccountCurrency.NONE,
    status = ContextAccountStatus.NONE,
    id = AccountId.NONE,
    lastTransactionTime = AccountLastTransactionTime.NONE,
)

private fun AccountTransactObject.toInternal(): Account = Account(
    id = this.id
        .toAccountId(),
)

private fun AccountUpdateObject.toInternal(): Account = Account(
    id = this.id
        .toAccountId(),
    description = this.description
        ?.let { AccountDescription(it) }
        ?: AccountDescription.NONE,
    ownerId = this.ownerId
        ?.let { AccountOwnerId(it.toString()) }
        ?: AccountOwnerId.NONE,
    balance = this.balance
        ?.let { AccountBalance(it) }
        ?: AccountBalance.NONE,
    currency = this.currency
        ?.let { AccountCurrency(it) }
        ?: AccountCurrency.NONE,
    status = this.status
        .fromTransport(),
)

private fun AccountStatus?.fromTransport(): ContextAccountStatus =
    when (this) {
        AccountStatus.ACTIVE -> ContextAccountStatus.ACTIVE
        AccountStatus.CLOSED -> ContextAccountStatus.CLOSED
        AccountStatus.FROZEN -> ContextAccountStatus.FROZEN
        null -> ContextAccountStatus.NONE
    }

private fun AccountTransaction.toInternal(): Transaction = Transaction(
    amount = this.amount
        ?.let { TransactionAmount(it) }
        ?: TransactionAmount.NONE,
    counterparty = this.counterparty
        ?.let { TransactionCounterparty(it) }
        ?: TransactionCounterparty.NONE,
    type = this.type
        .fromTransport(),
    description = this.description
        ?.let { TransactionDescription(it) }
        ?: TransactionDescription.NONE,
)

private fun TransactionType?.fromTransport(): ContextTransactionType =
    when (this) {
        TransactionType.INCOME -> ContextTransactionType.INCOME
        TransactionType.WITHDRAW -> ContextTransactionType.WITHDRAW
        null -> ContextTransactionType.NONE
    }
