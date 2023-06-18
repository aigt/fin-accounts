package aigt.finaccounts.common.models.transaction

import kotlin.jvm.JvmInline

@JvmInline
value class TransactionAmount(private val cents: Int) {
    fun asString() = cents.toString()
    fun asInt() = cents

    companion object {
        val NONE = TransactionAmount(0)
    }
}
