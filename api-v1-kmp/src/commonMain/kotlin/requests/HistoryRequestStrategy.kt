package aigt.finaccounts.api.v1.kmp.requests

import kotlinx.serialization.KSerializer
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.kmp.models.IRequest
import kotlin.reflect.KClass

object HistoryRequestStrategy: IRequestStrategy {
    override val discriminator: String = "history"
    override val clazz: KClass<out IRequest> = AccountHistoryRequest::class
    override val serializer: KSerializer<out IRequest> = AccountHistoryRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is AccountHistoryRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
