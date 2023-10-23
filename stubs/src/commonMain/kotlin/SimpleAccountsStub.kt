package aigt.finaccounts.stubs

import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountPermissionClient
import aigt.finaccounts.common.models.account.AccountStatus
import kotlinx.datetime.Instant

object SimpleAccountsStub {

    val ACCOUNT_ID = AccountId(id = "10002000300040005000")
    val ACCOUNT_DESCRIPTION = AccountDescription("Простой аккаунт")
    val ACCOUNT_OWNER_ID =
        AccountOwnerId("9deb6b8c-b797-4b34-9201-776ae1d3cf58")
    val ACCOUNT_BALANCE = AccountBalance(cents = 1200_00)
    val ACCOUNT_CURRENCY = AccountCurrency.RUB
    val ACCOUNT_STATUS = AccountStatus.ACTIVE
    val ACCOUNT_LAST_TRANSACTION_TIME = AccountLastTransactionTime(
        timestamp = Instant.parse("2023-06-26T18:43:00.123456789Z"),
    )
    val ACCOUNT_CLIENT_PERMISSIONS = mutableSetOf(AccountPermissionClient.READ)

    val SIMPLE_ACTIVE_ACCOUNT: Account
        get() = Account(
            ACCOUNT_ID,
            ACCOUNT_DESCRIPTION,
            ACCOUNT_OWNER_ID,
            ACCOUNT_BALANCE,
            ACCOUNT_CURRENCY,
            ACCOUNT_STATUS,
            ACCOUNT_LAST_TRANSACTION_TIME,
            ACCOUNT_CLIENT_PERMISSIONS,
        )
    val TRANSACT_ACCOUNT: Account
        get() = Account(
            ACCOUNT_ID,
            ACCOUNT_DESCRIPTION,
            ACCOUNT_OWNER_ID,
            ACCOUNT_BALANCE,
            ACCOUNT_CURRENCY,
            ACCOUNT_STATUS,
            ACCOUNT_LAST_TRANSACTION_TIME,
            ACCOUNT_CLIENT_PERMISSIONS,
        )
    val SIMPLE_FROZEN_ACCOUNT =
        SIMPLE_ACTIVE_ACCOUNT.copy(status = AccountStatus.FROZEN)
}
