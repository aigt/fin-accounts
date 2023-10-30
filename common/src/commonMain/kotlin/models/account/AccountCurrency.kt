package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountCurrency(private val code: String) {
    init {
        require(code.isNotEmpty()) { "currency code length must be >= 1: '$code'" }
        require(code.length <= 3) { "currency code length must be <= 3: '$code'" }
    }

    fun asString() = code

    companion object {
        val NONE = AccountCurrency("-")
        val RUB = AccountCurrency("RUB")
    }
}
