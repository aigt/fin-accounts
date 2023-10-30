package aigt.finaccounts.common.models.account

import kotlin.jvm.JvmInline

@JvmInline
value class AccountDescription(private val description: String) {
    fun asString() = description

    fun isNotEmpty(): Boolean = description.isNotEmpty()

    fun matches(regex: Regex): Boolean = description matches regex

    fun trim(): AccountDescription = AccountDescription(description.trim())

    companion object {
        val NONE = AccountDescription("")
    }
}
