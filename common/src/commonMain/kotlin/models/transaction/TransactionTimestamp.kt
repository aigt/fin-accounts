package aigt.finaccounts.common.models.transaction

import aigt.finaccounts.common.NONE
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline

@JvmInline
value class TransactionTimestamp(private val startTime: Instant) {
    fun asString() = startTime.toString()

    companion object {
        val NONE = TransactionTimestamp(Instant.NONE)
    }
}
