package aigt.api.kmp.v1.responses

import aigt.api.kmp.v1.IApiStrategy
import aigt.api.kmp.v1.models.IResponse

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
