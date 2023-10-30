package aigt.finaccounts.common.models.transaction

import kotlin.jvm.JvmInline

@JvmInline
value class TransactionCounterparty(private val id: String) {
    fun asString() = id

    fun matches(regex: Regex): Boolean = id.matches(regex)

    companion object {
        val NONE = TransactionCounterparty("")
    }
}
