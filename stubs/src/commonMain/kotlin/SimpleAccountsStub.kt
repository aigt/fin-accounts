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
    val SIMPLE_ACTIVE_ACCOUNT: Account
        get() = Account(
            id = AccountId(id = "10002000300040005000"),
            description = AccountDescription(description = "Простой аккаунт"),
            ownerId = AccountOwnerId(id = "9deb6b8c-b797-4b34-9201-776ae1d3cf58"),
            balance = AccountBalance(cents = 1200_00),
            currency = AccountCurrency(code = "RUB"),
            status = AccountStatus.ACTIVE,
            lastTransactionTime = AccountLastTransactionTime(
                timestamp = Instant.parse("2023-06-26T18:43:00.123456789Z"),
            ),
            permissionsClient = mutableSetOf(AccountPermissionClient.READ),
        )
    val TRANSACT_ACCOUNT: Account
        get() = Account(
            id = AccountId(id = "10002000300040005000"),
            description = AccountDescription(description = "Простой аккаунт"),
            ownerId = AccountOwnerId(id = "9deb6b8c-b797-4b34-9201-776ae1d3cf58"),
            balance = AccountBalance(cents = 1200_00),
            currency = AccountCurrency(code = "RUB"),
            status = AccountStatus.ACTIVE,
            lastTransactionTime = AccountLastTransactionTime(
                timestamp = Instant.parse("2023-06-26T18:43:00.123456789Z"),
            ),
            permissionsClient = mutableSetOf(AccountPermissionClient.READ),
        )
    val SIMPLE_FROZEN_ACCOUNT =
        SIMPLE_ACTIVE_ACCOUNT.copy(status = AccountStatus.FROZEN)
}
