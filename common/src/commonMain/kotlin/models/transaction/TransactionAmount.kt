package aigt.finaccounts.common.models.transaction

import kotlin.jvm.JvmInline

@JvmInline
value class TransactionAmount(private val cents: Int) {
    fun asString() = cents.toString()

    companion object {
        val ZERO = TransactionAmount(0)
    }
}
