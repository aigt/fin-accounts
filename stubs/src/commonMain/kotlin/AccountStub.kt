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
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.accountfilter.OwnerIdFilter
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.common.models.transaction.TransactionId
import aigt.finaccounts.common.models.transaction.TransactionTimestamp
import aigt.finaccounts.common.models.transaction.TransactionType
import aigt.finaccounts.stubs.SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
import aigt.finaccounts.stubs.SimpleAccountsStub.TRANSACT_ACCOUNT
import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.toInstant


object AccountStub {
    fun get(): Account = SIMPLE_ACTIVE_ACCOUNT.copy()

    fun transact(): Account = TRANSACT_ACCOUNT.copy()

    fun prepareResult(block: Account.() -> Unit): Account = get().apply(block)

    fun prepareSimpleTransactonsList(accountId: AccountId) =
        prepareTransactionsList(
            listSize = 7,
            filter = "на чай простая",
            accountId = TransactionAccountId(accountId.asString()),
            type = TransactionType.WITHDRAW,
        )

    fun prepareTransactionsList(
        listSize: Int,
        filter: String,
        accountId: TransactionAccountId,
        type: TransactionType,
    ) = MutableList(listSize) { index ->
        transaction(
            id = TransactionId(
                id = "1754624d-f266-4d0c-b72b-${
                    index.toString().padStart(12, '0')
                }",
            ),
            amount = TransactionAmount(cents = (index + 1) * 100_00),
            filter = "$filter index: $index",
            accountId = accountId,
            counterparty = TransactionCounterparty(id = "99992000300040005000"),
            type = type,
        )
    }

    fun prepareAccountsList(
        listSize: Int,
        currency: AccountCurrency,
        balance: AccountBalance,
        filter: AccountFilter,
        lastTransactionTime: AccountLastTransactionTime = AccountLastTransactionTime(
            timestamp = now(),
        ),
    ) = MutableList(listSize) { index ->
        account(
            ownerId = AccountOwnerId(
                id = filter.ownerId.takeIf { it != OwnerIdFilter.NONE }
                    ?.asString()
                    ?: uuid4().toString(),
            ),
            balance = balance,
            currency = currency,
            status = AccountStatus.ACTIVE,
            filter = filter.searchString.asString(),
            lastTransactionTime = lastTransactionTime,
            index = index + 1,
        )
    }

    private fun account(
        ownerId: AccountOwnerId,
        balance: AccountBalance,
        currency: AccountCurrency,
        status: AccountStatus,
        filter: String,
        lastTransactionTime: AccountLastTransactionTime,
        index: Int = 0,
    ) = Account(
        id = AccountId(id = "1000200030004000500${index}"),
        description = AccountDescription("desc $filter"),
        ownerId = ownerId,
        balance = balance,
        currency = currency,
        status = status,
        lastTransactionTime = lastTransactionTime,
        permissionsClient = mutableSetOf(AccountPermissionClient.READ),
    )

    private fun transaction(
        id: TransactionId,
        amount: TransactionAmount,
        filter: String,
        accountId: TransactionAccountId,
        counterparty: TransactionCounterparty,
        type: TransactionType,
    ) = Transaction(
        id = id,
        amount = amount,
        accountId = accountId,
        counterparty = counterparty,
        timestamp = TransactionTimestamp(timestamp = "2023-07-07T18:43:00.123456789Z".toInstant()),
        type = type,
        description = TransactionDescription("desc $filter $accountId"),
    )

}
