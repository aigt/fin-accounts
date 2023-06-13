package aigt.api.kmp.v1.requests

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountCreateRequest
import aigt.api.kmp.v1.models.IRequest
import kotlin.reflect.KClass

object CreateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IRequest> = AccountCreateRequest::class
    override val serializer: KSerializer<out IRequest> = AccountCreateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is AccountCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
