package aigt.api.kmp.v1.requests

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountReadRequest
import aigt.api.kmp.v1.models.IRequest
import kotlin.reflect.KClass

object ReadRequestStrategy: IRequestStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IRequest> = AccountReadRequest::class
    override val serializer: KSerializer<out IRequest> = AccountReadRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is AccountReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
