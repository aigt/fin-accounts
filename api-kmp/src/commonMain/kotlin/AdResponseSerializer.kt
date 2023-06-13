package aigt.api.kmp.v1

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import aigt.api.kmp.v1.models.IResponse
import aigt.api.kmp.v1.responses.IResponseStrategy


val AdResponseSerializer = ResponseSerializer(AdResponseSerializerBase)

private object AdResponseSerializerBase : JsonContentPolymorphicSerializer<IResponse>(IResponse::class) {
    private const val discriminator = "responseType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IResponse> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return IResponseStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IResponse::class} implementation"
            )
    }
}

class ResponseSerializer<T : IResponse>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        IResponseStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IResponse instance in ResponseSerializer"
            )
}
