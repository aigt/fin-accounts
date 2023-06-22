package aigt.finaccounts.common.models.accountfilter

import kotlin.jvm.JvmInline

@JvmInline
value class OwnerIdFilter(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = OwnerIdFilter("")
    }
}
