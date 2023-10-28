package aigt.finaccounts.common.models.transaction

data class Transaction(
    var id: TransactionId = TransactionId.NONE,
    var amount: TransactionAmount = TransactionAmount.NONE,
    var accountId: TransactionAccountId = TransactionAccountId.NONE,
    var counterparty: TransactionCounterparty = TransactionCounterparty.NONE,
    var timestamp: TransactionTimestamp = TransactionTimestamp.NONE,
    var type: TransactionType = TransactionType.NONE,
    var description: TransactionDescription = TransactionDescription.NONE,
) {

    fun deepCopy(): Transaction = copy(amount = this.amount)

    companion object {
        val NONE = Transaction()
    }
}
