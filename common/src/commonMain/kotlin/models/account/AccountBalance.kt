package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountBalance(private val cents: Int) {
    fun asString() = cents.toString()

    companion object {
        val ZERO = AccountBalance(0)
    }
}
