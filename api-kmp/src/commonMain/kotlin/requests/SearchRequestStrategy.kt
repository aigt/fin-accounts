package aigt.api.kmp.v1.requests

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountSearchRequest
import aigt.api.kmp.v1.models.IRequest
import kotlin.reflect.KClass

object SearchRequestStrategy: IRequestStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IRequest> = AccountSearchRequest::class
    override val serializer: KSerializer<out IRequest> = AccountSearchRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is AccountSearchRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
