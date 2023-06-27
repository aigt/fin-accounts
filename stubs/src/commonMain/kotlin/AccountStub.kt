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


object AccountStub {
    fun get(): Account = SIMPLE_ACTIVE_ACCOUNT.copy()

    fun transact(): Account = TRANSACT_ACCOUNT.copy()

    fun prepareResult(block: Account.() -> Unit): Account = get().apply(block)

    fun prepareTransactionsList(
        listSize: Int,
        filter: String,
        accountId: TransactionAccountId,
        type: TransactionType,
    ) = List(listSize) { index ->
        transaction(
            id = TransactionId(id = uuid4().toString()),
            amount = TransactionAmount(cents = (index + 1) * 100_00),
            filter = filter,
            accountId = accountId,
            counterparty = TransactionCounterparty(id = "3242d9d3-39b3-4fa8-95d4-c847f2bbc25b"),
            type = type,
        )
    }

    fun prepareAccountsList(
        listSize: Int,
        currency: AccountCurrency,
        balance: AccountBalance,
        filter: String,
    ) = List(listSize) { index ->
        account(
            ownerId = AccountOwnerId(id = uuid4().toString()),
            balance = balance,
            currency = currency,
            status = AccountStatus.ACTIVE,
            filter = filter,
        )
    }

    private fun account(
        ownerId: AccountOwnerId,
        balance: AccountBalance,
        currency: AccountCurrency,
        status: AccountStatus,
        filter: String,
    ) = Account(
        id = AccountId(id = uuid4().toString()),
        description = AccountDescription("desc $filter"),
        ownerId = ownerId,
        balance = balance,
        currency = currency,
        status = status,
        lastTransactionTime = AccountLastTransactionTime(timestamp = now()),
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
        timestamp = TransactionTimestamp(timestamp = now()),
        type = type,
        description = TransactionDescription(id = "desc $filter $accountId"),
    )

}