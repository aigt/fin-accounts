package aigt.finaccounts.logging.v1.mapper

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountStatus
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.accountfilter.OwnerIdFilter
import aigt.finaccounts.common.models.accountfilter.SearchStringFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.common.models.transaction.TransactionId
import aigt.finaccounts.common.models.transaction.TransactionTimestamp
import aigt.finaccounts.common.models.transaction.TransactionType
import aigt.finaccounts.logging.v1.api.models.AccountFilterLog
import aigt.finaccounts.logging.v1.api.models.AccountLog
import aigt.finaccounts.logging.v1.api.models.CommonLogModel
import aigt.finaccounts.logging.v1.api.models.ErrorLogModel
import aigt.finaccounts.logging.v1.api.models.FinAccountLogModel
import aigt.finaccounts.logging.v1.api.models.TransactionLog
import kotlinx.datetime.Clock


fun FinAccountsContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "finaccounts",
    account = toLog(),
    errors = errors.map { it.toLog() },
)


fun FinAccountsContext.toLog(): FinAccountLogModel? = FinAccountLogModel(
    requestId = requestId
        .takeIf { it != RequestId.NONE }
        ?.asString(),
    command = command
        .takeIf { it != ContextCommand.NONE }
        ?.let { FinAccountLogModel.Command.valueOf(it.name) },
    accountFilter = accountFilter
        .takeIf { it != AccountFilter.NONE }
        ?.toLog(),
    accountRequest = accountRequest
        .takeIf { it != Account.NONE }
        ?.toLog(),
    transactionRequest = transactionRequest
        .takeIf { it != Transaction.NONE }
        ?.toLog(),
    accountResponse = accountResponse
        .takeIf { it != Account.NONE }
        ?.toLog(),
    accountsResponse = accountsResponse
        .filter { it != Account.NONE }
        .map { it.toLog() }
        .takeIf { it.isNotEmpty() },
    historyResponse = historyResponse
        .filter { it != Transaction.NONE }
        .map { it.toLog() }
        .takeIf { it.isNotEmpty() },
).takeIf { it != FinAccountLogModel() }


private fun Transaction.toLog(): TransactionLog = TransactionLog(
    id = id
        .takeIf { it != TransactionId.NONE }
        ?.asString(),
    amount = amount
        .takeIf { it != TransactionAmount.NONE }
        ?.asString(),
    accountId = accountId
        .takeIf { it != TransactionAccountId.NONE }
        ?.asString(),
    counterparty = counterparty
        .takeIf { it != TransactionCounterparty.NONE }
        ?.asString(),
    timestamp = timestamp
        .takeIf { it != TransactionTimestamp.NONE }
        ?.asString(),
    type = type
        .takeIf { it != TransactionType.NONE }
        ?.let { TransactionLog.Type.valueOf(it.name) },
    description = description
        .takeIf { it != TransactionDescription.NONE }
        ?.asString(),
)


private fun AccountFilter.toLog(): AccountFilterLog = AccountFilterLog(
    searchString = searchString
        .takeIf { it != SearchStringFilter.NONE }
        ?.asString(),
    ownerId = ownerId
        .takeIf { it != OwnerIdFilter.NONE }
        ?.asString(),
)


private fun Account.toLog(): AccountLog = AccountLog(
    id = id
        .takeIf { it != AccountId.NONE }
        ?.asString(),
    description = description
        .takeIf { it != AccountDescription.NONE }
        ?.asString(),
    ownerId = ownerId
        .takeIf { it != AccountOwnerId.NONE }
        ?.asString(),
    balance = balance
        .takeIf { it != AccountBalance.NONE }
        ?.asString(),
    currency = currency
        .takeIf { it != AccountCurrency.NONE }
        ?.asString(),
    status = status
        .takeIf { it != AccountStatus.NONE }
        ?.let { AccountLog.Status.valueOf(it.name) },
    lastTransactionTime = lastTransactionTime
        .takeIf { it != AccountLastTransactionTime.NONE }
        ?.asString(),
    permissionsClient = permissionsClient
        .takeIf { it.isNotEmpty() }
        ?.map { AccountLog.PermissionsClient.valueOf(it.name) }
        ?.toSet(),
)


fun ContextError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
)
