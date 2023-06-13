package aigt.api.kmp.v1.responses

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountCreateResponse
import aigt.api.kmp.v1.models.IResponse
import kotlin.reflect.KClass

object CreateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IResponse> = AccountCreateResponse::class
    override val serializer: KSerializer<out IResponse> = AccountCreateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AccountCreateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
