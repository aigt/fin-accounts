package aigt.finaccounts.api.v1.kmp

import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.requests.IRequestStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


val accountRequestSerializer: RequestSerializer<IRequest> =
    RequestSerializer(AccountRequestSerializerBase)

private object AccountRequestSerializerBase :
    JsonContentPolymorphicSerializer<IRequest>(IRequest::class) {

    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IRequest> {
        val discriminatorValue =
            element.jsonObject[discriminator]?.jsonPrimitive?.content
        return IRequestStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                    "property of ${IRequest::class} implementation",
            )
    }
}

class RequestSerializer<T : IRequest>(private val serializer: KSerializer<T>) :
    KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        IRequestStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IRequest instance in RequestSerializer",
            )
}
