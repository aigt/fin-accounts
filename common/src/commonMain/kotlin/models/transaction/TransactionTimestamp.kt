package aigt.finaccounts.common.models.transaction

import aigt.finaccounts.common.NONE
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline

@JvmInline
value class TransactionTimestamp(private val timestamp: Instant) {
    fun asString() = timestamp.toString()

    companion object {
        val NONE = TransactionTimestamp(Instant.NONE)
    }
}
