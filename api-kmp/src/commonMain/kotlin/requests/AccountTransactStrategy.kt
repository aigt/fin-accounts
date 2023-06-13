package aigt.api.kmp.v1.requests

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountTransactRequest
import aigt.api.kmp.v1.models.IRequest
import kotlin.reflect.KClass

object AccountTransactStrategy: IRequestStrategy {
    override val discriminator: String = "transact"
    override val clazz: KClass<out IRequest> = AccountTransactRequest::class
    override val serializer: KSerializer<out IRequest> = AccountTransactRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is AccountTransactRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
