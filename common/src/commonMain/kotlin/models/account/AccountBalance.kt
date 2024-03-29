package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountBalance(private val cents: Int?) {
    fun asString(): String = cents?.toString() ?: ""
    fun asInt(): Int? = cents

    companion object {
        val NONE = AccountBalance(null)
    }
}
