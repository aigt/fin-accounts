package aigt.api.v1.kmp.requests

import kotlinx.serialization.KSerializer
import aigt.api.v1.kmp.models.AccountUpdateRequest
import aigt.api.v1.kmp.models.IRequest
import kotlin.reflect.KClass

object UpdateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IRequest> = AccountUpdateRequest::class
    override val serializer: KSerializer<out IRequest> = AccountUpdateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is AccountUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
