package aigt.finaccounts.common.models.transaction

import kotlin.jvm.JvmInline

@JvmInline
value class TransactionCounterparty(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TransactionCounterparty("")
    }
}
