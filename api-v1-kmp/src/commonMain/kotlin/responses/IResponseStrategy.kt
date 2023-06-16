package aigt.api.v1.kmp.responses

import aigt.api.v1.kmp.IApiStrategy
import aigt.api.v1.kmp.models.IResponse

sealed interface IResponseStrategy: IApiStrategy<IResponse> {
    companion object {
        private val members = listOf(
            CreateResponseStrategy,
            ReadResponseStrategy,
            UpdateResponseStrategy,
            HistoryResponseStrategy,
            SearchResponseStrategy,
            TransactResponseStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
