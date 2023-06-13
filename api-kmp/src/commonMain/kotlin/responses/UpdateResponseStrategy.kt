package aigt.api.kmp.v1.responses

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountUpdateResponse
import aigt.api.kmp.v1.models.IResponse
import kotlin.reflect.KClass

object UpdateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IResponse> = AccountUpdateResponse::class
    override val serializer: KSerializer<out IResponse> = AccountUpdateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AccountUpdateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
