package aigt.finaccounts.common.models.transaction

import kotlin.jvm.JvmInline

@JvmInline
value class TransactionDescription(private val description: String) {
    fun asString() = description

    fun matches(regex: Regex): Boolean = description matches regex

    fun trim(): TransactionDescription =
        TransactionDescription(description.trim())

    companion object {
        val NONE = TransactionDescription("")
    }
}
