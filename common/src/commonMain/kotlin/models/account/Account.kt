package aigt.finaccounts.common.models.account

data class Account(
    var id: AccountId = AccountId.NONE,
    var description: AccountDescription = AccountDescription.NONE,
    var ownerId: AccountOwnerId = AccountOwnerId.NONE,
    var balance: AccountBalance = AccountBalance.NONE,
    var currency: AccountCurrency = AccountCurrency.NONE,
    var status: AccountStatus = AccountStatus.NONE,
    var lastTransactionTime: AccountLastTransactionTime = AccountLastTransactionTime.NONE,
    val permissionsClient: MutableSet<AccountPermissionClient> = mutableSetOf(),
)
