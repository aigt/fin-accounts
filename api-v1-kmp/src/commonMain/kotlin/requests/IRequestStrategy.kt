package aigt.api.v1.kmp.requests

import aigt.api.v1.kmp.IApiStrategy
import aigt.api.v1.kmp.models.IRequest

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
