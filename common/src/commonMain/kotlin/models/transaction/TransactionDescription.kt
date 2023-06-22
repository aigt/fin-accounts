package aigt.finaccounts.common.models.transaction

import kotlin.jvm.JvmInline

@JvmInline
value class TransactionDescription(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TransactionDescription("")
    }
}
