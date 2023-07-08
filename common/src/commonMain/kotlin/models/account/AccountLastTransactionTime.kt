package aigt.finaccounts.common.models.account

import aigt.finaccounts.common.NONE
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline

@JvmInline
value class AccountLastTransactionTime(private val timestamp: Instant) {
    fun asString() = timestamp.toString()

    companion object {
        val NONE = AccountLastTransactionTime(Instant.NONE)
    }
}
