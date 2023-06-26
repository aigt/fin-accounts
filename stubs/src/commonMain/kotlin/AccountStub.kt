package aigt.finaccounts.stubs

import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.common.models.transaction.TransactionId
import aigt.finaccounts.common.models.transaction.TransactionTimestamp
import aigt.finaccounts.common.models.transaction.TransactionType
import aigt.finaccounts.stubs.SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT
import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock.System.now


object AccountStub {
    fun get(): Account = SIMPLE_ACTIVE_ACCOUNT.copy()

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
