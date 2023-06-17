package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountOwnerId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = AccountOwnerId("")
    }
}
