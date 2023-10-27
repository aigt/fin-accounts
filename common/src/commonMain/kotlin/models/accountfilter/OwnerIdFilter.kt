package aigt.finaccounts.common.models.accountfilter

import kotlin.jvm.JvmInline

@JvmInline
value class OwnerIdFilter(private val id: String) {
    fun asString() = id

    fun matches(regex: Regex): Boolean = id.matches(regex)

    companion object {
        val NONE = OwnerIdFilter("")
    }
}
