package aigt.api.kmp.v1.responses

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountSearchResponse
import aigt.api.kmp.v1.models.IResponse
import kotlin.reflect.KClass

object SearchResponseStrategy: IResponseStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IResponse> = AccountSearchResponse::class
    override val serializer: KSerializer<out IResponse> = AccountSearchResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AccountSearchResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
