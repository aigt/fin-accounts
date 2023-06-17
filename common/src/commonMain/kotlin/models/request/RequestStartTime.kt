package aigt.finaccounts.common.models.request

import aigt.finaccounts.common.NONE
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline

@JvmInline
value class RequestStartTime(private val startTime: Instant) {
    fun asString() = startTime.toString()

    companion object {
        val NONE = RequestStartTime(Instant.NONE)
    }
}
