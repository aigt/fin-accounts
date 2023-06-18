package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountBalance(private val cents: Int) {
    fun asString() = cents.toString()
    fun asInt() = cents

    companion object {
        val NONE = AccountBalance(0)
    }
}
