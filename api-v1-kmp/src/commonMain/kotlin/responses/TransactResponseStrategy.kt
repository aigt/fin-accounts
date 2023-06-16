package aigt.api.v1.kmp.responses

import kotlinx.serialization.KSerializer
import aigt.api.v1.kmp.models.AccountTransactResponse
import aigt.api.v1.kmp.models.IResponse
import kotlin.reflect.KClass

object TransactResponseStrategy: IResponseStrategy {
    override val discriminator: String = "transact"
    override val clazz: KClass<out IResponse> = AccountTransactResponse::class
    override val serializer: KSerializer<out IResponse> = AccountTransactResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AccountTransactResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
