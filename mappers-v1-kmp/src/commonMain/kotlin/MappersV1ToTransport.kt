package aigt.finaccounts.mappers.kmp.v1

import aigt.finaccounts.api.v1.kmp.models.*
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
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.common.models.transaction.TransactionTimestamp
import aigt.finaccounts.mappers.kmp.v1.exceptions.MappingUnknownCommand
import aigt.finaccounts.common.models.account.AccountStatus as CtxAccountStatus
import aigt.finaccounts.common.models.transaction.TransactionType as CtxTransactionType

fun FinAccountsContext.toTransportResponse(): IResponse =
    when (val cmd = command) {
        ContextCommand.CREATE -> toTransportCreate()
        ContextCommand.READ -> toTransportRead()
        ContextCommand.UPDATE -> toTransportUpdate()
        ContextCommand.SEARCH -> toTransportSearch()
        ContextCommand.HISTORY -> toTransportHistory()
        ContextCommand.TRANSACT -> toTransportTransact()
        ContextCommand.NONE -> throw MappingUnknownCommand(cmd)
    }

fun FinAccountsContext.toTransportCreate() = AccountCreateResponse(
    responseType = "create",
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
    responseType = "read",
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
    responseType = "update",
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

fun FinAccountsContext.toTransportHistory() = AccountHistoryResponse(
    responseType = "history",
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
    responseType = "search",
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

fun FinAccountsContext.toTransportTransact() = AccountTransactResponse(
    responseType = "transact",
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

fun FinAccountsContext.toTransportResponseResult(): ResponseResult =
    when (state) {
        ContextState.RUNNING -> ResponseResult.SUCCESS
        else -> ResponseResult.ERROR
    }

fun List<Account>.toTransportAccounts(): List<AccountResponseObject>? = this
    .map { it.toTransportAccount() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Account.toTransportAccount(): AccountResponseObject =
    AccountResponseObject(
        description = description
            .takeIf { it != AccountDescription.NONE }
            ?.asString(),
        ownerId = ownerId
            .takeIf { it != AccountOwnerId.NONE }
            ?.asString(),
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

fun List<Transaction>.toTransportHistory(): List<AccountTransaction>? =
    this
        .map { it.toTransportTransaction() }
        .toList()
        .takeIf { it.isNotEmpty() }

private fun Transaction.toTransportTransaction(): AccountTransaction =
    AccountTransaction(
        type = type
            .toTransportTransactionType(),
        amount = amount
            .takeIf { it != TransactionAmount.NONE }
            ?.asInt(),
        counterparty = counterparty
            .takeIf { it != TransactionCounterparty.NONE }
            ?.asString(),
        description = description
            .takeIf { it != TransactionDescription.NONE }
            ?.asString(),
        timestamp = timestamp
            .takeIf { it != TransactionTimestamp.NONE }
            ?.asString(),
    )

private fun CtxTransactionType.toTransportTransactionType(): TransactionType? =
    when (this) {
        CtxTransactionType.INCOME -> TransactionType.INCOME
        CtxTransactionType.WITHDRAW -> TransactionType.WITHDRAW
        CtxTransactionType.NONE -> null
    }

private fun MutableSet<AccountPermissionClient>.toTransportPermissions(): Set<AccountPermissions>? =
    this
        .map { it.toTransportPermission() }
        .toSet()
        .takeIf { it.isNotEmpty() }

private fun AccountPermissionClient.toTransportPermission(): AccountPermissions =
    when (this) {
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
