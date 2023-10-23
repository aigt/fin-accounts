package aigt.finaccounts.stubs

import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.common.models.transaction.TransactionAmount
import aigt.finaccounts.common.models.transaction.TransactionCounterparty
import aigt.finaccounts.common.models.transaction.TransactionDescription
import aigt.finaccounts.common.models.transaction.TransactionId
import aigt.finaccounts.common.models.transaction.TransactionTimestamp
import aigt.finaccounts.common.models.transaction.TransactionType
import kotlinx.datetime.Instant

object TransactionStub {

    fun getSimpleTransactionStub(
        accountId: TransactionAccountId = TransactionAccountId("11112000300040005111"),
    ) = Transaction(
        TransactionId("1754624d-f266-4d0c-b72b-123123123123"),
        TransactionAmount(500_27),
        accountId,
        TransactionCounterparty("99992000300040005123"),
        TransactionTimestamp(Instant.parse("2010-11-01T18:43:00.123456781Z")),
        TransactionType.WITHDRAW,
        TransactionDescription("description of simple transaction stub"),
    )

    fun getTransactActionTransactionStub() = Transaction(
        TransactionId.NONE,
        TransactionAmount(500_27),
        TransactionAccountId.NONE,
        TransactionCounterparty("99992000300040005123"),
        TransactionTimestamp.NONE,
        TransactionType.WITHDRAW,
        TransactionDescription("description of simple transaction stub"),
    )

}
