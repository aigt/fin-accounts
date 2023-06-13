package aigt.api.kmp.v1.responses

import kotlinx.serialization.KSerializer
import aigt.api.kmp.v1.models.AccountHistoryResponse
import aigt.api.kmp.v1.models.IResponse
import kotlin.reflect.KClass

object HistoryResponseStrategy: IResponseStrategy {
    override val discriminator: String = "history"
    override val clazz: KClass<out IResponse> = AccountHistoryResponse::class
    override val serializer: KSerializer<out IResponse> = AccountHistoryResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AccountHistoryResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
