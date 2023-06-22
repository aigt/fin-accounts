package aigt.finaccounts.common.models.account

import aigt.finaccounts.common.NONE
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline

@JvmInline
value class AccountLastTransactionTime(private val startTime: Instant) {
    fun asString() = startTime.toString()

    companion object {
        val NONE = AccountLastTransactionTime(Instant.NONE)
    }
}
