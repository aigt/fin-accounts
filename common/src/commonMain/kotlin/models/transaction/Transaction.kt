package aigt.finaccounts.common.models.transaction

data class Transaction (
    var amount: TransactionAmount = TransactionAmount.ZERO,
    var counterpaty: TransactionCounterparty = TransactionCounterparty.NONE,
    var timestamp: TransactionTimestamp = TransactionTimestamp.NONE,
    var type: TransactionType = TransactionType.NONE,
    var description: TransactionDescription = TransactionDescription.NONE,
)
