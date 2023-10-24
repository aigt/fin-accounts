package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountOwnerId(private val id: String) {
    fun asString() = id

    fun isEmpty(): Boolean = id.isEmpty()

    companion object {
        val NONE = AccountOwnerId("")
    }
}
