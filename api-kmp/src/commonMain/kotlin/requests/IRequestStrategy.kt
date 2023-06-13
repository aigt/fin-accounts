package aigt.api.kmp.v1.requests

import aigt.api.kmp.v1.IApiStrategy
import aigt.api.kmp.v1.models.IRequest

sealed interface IRequestStrategy: IApiStrategy<IRequest> {
    companion object {
        private val members: List<IRequestStrategy> = listOf(
            CreateRequestStrategy,
            ReadRequestStrategy,
            UpdateRequestStrategy,
            HistoryRequestStrategy,
            SearchRequestStrategy,
            AccountTransactStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
