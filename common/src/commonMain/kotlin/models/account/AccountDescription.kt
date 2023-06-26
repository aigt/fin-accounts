package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountDescription(private val description: String) {
    fun asString() = description

    companion object {
        val NONE = AccountDescription("")
    }
}
