package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountId(private val id: String?) {
    fun asString() = id ?: ""

    fun matches(regex: Regex): Boolean = id?.matches(regex) ?: false

    companion object {
        val NONE = AccountId(null)
    }
}
