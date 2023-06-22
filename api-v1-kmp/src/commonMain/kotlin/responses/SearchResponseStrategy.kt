package aigt.finaccounts.api.v1.kmp.responses

import kotlinx.serialization.KSerializer
import aigt.finaccounts.api.v1.kmp.models.AccountSearchResponse
import aigt.finaccounts.api.v1.kmp.models.IResponse
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
