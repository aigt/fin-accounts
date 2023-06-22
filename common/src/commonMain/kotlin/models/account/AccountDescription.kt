package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountDescription(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = AccountDescription("")
    }
}
