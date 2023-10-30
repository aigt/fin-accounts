package aigt.finaccounts.common.helpers

import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountStatus

fun makeCreateRequestAccount(
    description: AccountDescription,
    ownerId: AccountOwnerId,
    currency: AccountCurrency,
) = Account(
    description = description,
    ownerId = ownerId,
    currency = currency,
)

fun makeHistoryRequestAccount(id: AccountId) = Account(id = id)

fun makeReadRequestAccount(id: AccountId) = Account(id = id)

fun makeTransactRequestAccount(id: AccountId) = Account(id = id)

fun makeUpdateRequestAccount(
    id: AccountId,
    description: AccountDescription,
    ownerId: AccountOwnerId,
    currency: AccountCurrency,
    balance: AccountBalance,
    status: AccountStatus,
) = Account(
    id = id,
    description = description,
    ownerId = ownerId,
    currency = currency,
    balance = balance,
    status = status,
)
