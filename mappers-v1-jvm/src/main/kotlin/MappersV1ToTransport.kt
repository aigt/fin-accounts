package aigt.finaccounts.mappers.v1

import aigt.finaccounts.api.v1.jackson.models.AccountCreateResponse
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryResponse
import aigt.finaccounts.api.v1.jackson.models.AccountPermissions
import aigt.finaccounts.api.v1.jackson.models.AccountReadResponse
import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountSearchResponse
import aigt.finaccounts.api.v1.jackson.models.AccountStatus
import aigt.finaccounts.api.v1.jackson.models.AccountTransactionResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountTransactResponse
import aigt.finaccounts.api.v1.jackson.models.TransactionType
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateResponse
import aigt.finaccounts.api.v1.jackson.models.IResponse
import aigt.finaccounts.api.v1.jackson.models.ResponseResult
import aigt.finaccounts.api.v1.jackson.models.Error
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountPermissionClient
import aigt.finaccounts.common.models.account.AccountStatus as CtxAccountStatus
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.common.models.transaction.TransactionTimestamp
import aigt.finaccounts.common.models.transaction.TransactionType as CtxTransactionType
import aigt.finaccounts.mappers.v1.exceptions.MappingUnknownCommand
import java.util.*

fun FinAccountsContext.toTransportResponse(): IResponse = when (val cmd = command) {
    ContextCommand.CREATE -> toTransportCreate()
    ContextCommand.READ -> toTransportRead()
    ContextCommand.UPDATE -> toTransportUpdate()
    ContextCommand.HISTORY -> toTransportDelete()
    ContextCommand.SEARCH -> toTransportSearch()
    ContextCommand.TRANSACT -> toTransportOffers()
    ContextCommand.NONE -> throw MappingUnknownCommand(cmd)
}

fun FinAccountsContext.toTransportCreate() = AccountCreateResponse(
    requestId = this.requestId
        .asString()
        .takeIf { it.isNotBlank() },
    result = this
        .toTransportResponseResult(),
    errors = errors
        .toTransportErrors(),
    account = accountResponse
        .toTransportAccount(),
)

fun FinAccountsContext.toTransportRead() = AccountReadResponse(
    requestId = this.requestId
        .asString()
        .takeIf { it.isNotBlank() },
    result = this
        .toTransportResponseResult(),
    errors = errors
        .toTransportErrors(),
    account = accountResponse
        .toTransportAccount(),
)

fun FinAccountsContext.toTransportUpdate() = AccountUpdateResponse(
    requestId = this.requestId
        .asString()
        .takeIf { it.isNotBlank() },
    result = this
        .toTransportResponseResult(),
    errors = errors
        .toTransportErrors(),
    account = accountResponse
        .toTransportAccount(),
)

fun FinAccountsContext.toTransportDelete() = AccountHistoryResponse(
    requestId = this.requestId
        .asString()
        .takeIf { it.isNotBlank() },
    result = this
        .toTransportResponseResult(),
    errors = errors
        .toTransportErrors(),
    account = accountResponse
        .toTransportAccount(),
    history = historyResponse
        .toTransportHistory(),
)

fun FinAccountsContext.toTransportSearch() = AccountSearchResponse(
    requestId = this.requestId
        .asString()
        .takeIf { it.isNotBlank() },
    result = this
        .toTransportResponseResult(),
    errors = errors
        .toTransportErrors(),
    accounts = accountsResponse
        .toTransportAccounts(),
)

fun FinAccountsContext.toTransportOffers() = AccountTransactResponse(
    requestId = this.requestId
        .asString()
        .takeIf { it.isNotBlank() },
    result = this
        .toTransportResponseResult(),
    errors = errors
        .toTransportErrors(),
    account = accountResponse
        .toTransportAccount(),
    history = historyResponse
        .toTransportHistory(),
)

fun FinAccountsContext.toTransportResponseResult(): ResponseResult = when(state) {
    ContextState.RUNNING -> ResponseResult.SUCCESS
    else -> ResponseResult.ERROR
}

fun List<Account>.toTransportAccounts(): List<AccountResponseObject>? = this
    .map { it.toTransportAccount() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Account.toTransportAccount(): AccountResponseObject = AccountResponseObject(
    description = description
        .takeIf { it != AccountDescription.NONE }
        ?.asString(),
    ownerId = ownerId
        .takeIf { it != AccountOwnerId.NONE }
        ?.let{ UUID.fromString(it.asString()) },
    balance = balance
        .takeIf { it != AccountBalance.NONE }
        ?.asInt(),
    currency = currency
        .takeIf { it != AccountCurrency.NONE }
        ?.asString(),
    status = status
        .toTransportStatus(),
    id = id
        .takeIf { it != AccountId.NONE }
        ?.asString(),
    lastTransaction = lastTransactionTime
        .takeIf { it != AccountLastTransactionTime.NONE }
        ?.asString(),
    permissions = permissionsClient
        .toTransportPermissions(),
)

fun CtxAccountStatus.toTransportStatus(): AccountStatus? = when (this) {
    CtxAccountStatus.ACTIVE -> AccountStatus.ACTIVE
    CtxAccountStatus.FROZEN -> AccountStatus.FROZEN
    CtxAccountStatus.CLOSED -> AccountStatus.CLOSED
    CtxAccountStatus.NONE -> null
}

fun List<Transaction>.toTransportHistory(): List<AccountTransactionResponseObject>? = this
    .map { it.toTransportTransaction() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Transaction.toTransportTransaction(): AccountTransactionResponseObject = AccountTransactionResponseObject(
    type = type
        .toTransportTransactionType(),
    amount = amount
        .takeIf { it != TransactionAmount.NONE }
        ?.asInt(),
    counterpaty = counterparty
        .takeIf { it != TransactionCounterparty.NONE }
        ?.asString(),
    description = description
        .takeIf { it != TransactionDescription.NONE }
        ?.asString(),
    timestamp = timestamp
        .takeIf { it != TransactionTimestamp.NONE }
        ?.asString(),
)

private fun CtxTransactionType.toTransportTransactionType(): TransactionType? = when (this) {
    CtxTransactionType.INCOME -> TransactionType.INCOME
    CtxTransactionType.WITHDRAW -> TransactionType.WITHDRAW
    CtxTransactionType.NONE -> null
}

private fun MutableSet<AccountPermissionClient>.toTransportPermissions(): Set<AccountPermissions>? = this
    .map { it.toTransportPermission() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun AccountPermissionClient.toTransportPermission(): AccountPermissions = when (this) {
    AccountPermissionClient.READ -> AccountPermissions.READ
    AccountPermissionClient.UPDATE -> AccountPermissions.UPDATE
    AccountPermissionClient.HISTORY -> AccountPermissions.HISTORY
    AccountPermissionClient.TRANSACT -> AccountPermissions.TRANSACT
}

private fun List<ContextError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportError() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ContextError.toTransportError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
