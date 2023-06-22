package aigt.finaccounts.common.models.account

import aigt.finaccounts.common.models.transaction.Transaction

data class Account(
    var id: AccountId = AccountId.NONE,
    var description: AccountDescription = AccountDescription.NONE,
    var ownerId: AccountOwnerId = AccountOwnerId.NONE,
    var balance: AccountBalance = AccountBalance.NONE,
    var currency: AccountCurrency = AccountCurrency.NONE,
    var status: AccountStatus = AccountStatus.NONE,
    var lastTransactionTime: AccountLastTransactionTime = AccountLastTransactionTime.NONE,
    var transaction: Transaction = Transaction(),
    val permissionsClient: MutableSet<AccountPermissionClient> = mutableSetOf(),
)
