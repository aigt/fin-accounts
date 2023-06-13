package aigt.api.kmp.v1.responses

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountReadResponse
import aigt.api.kmp.v1.models.IResponse
import kotlin.reflect.KClass

object ReadResponseStrategy: IResponseStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IResponse> = AccountReadResponse::class
    override val serializer: KSerializer<out IResponse> = AccountReadResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AccountReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
