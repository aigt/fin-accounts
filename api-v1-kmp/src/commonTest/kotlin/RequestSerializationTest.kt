package aigt.finaccounts.api.v1.kmp

import aigt.finaccounts.api.v1.kmp.models.AccountCreateObject
import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.kmp.models.IRequest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request: IRequest = AccountCreateRequest(
        requestType = "create",
        requestId = "123",
        debug = AccountDebug(
            mode = AccountRequestDebugMode.STUB,
            stub = AccountRequestDebugStubs.BAD_OWNER_ID,
        ),
        account = AccountCreateObject(
            ownerId = "cd565097-4b69-490e-b167-b59128475562",
            description = "stub",
            currency = "RUB",
        ),
    )

    @Test
    fun serialize() {
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
        val json = apiV1Mapper.encodeToString(request)

        println(json)

        assertContains(
            json,
            Regex("\"ownerId\":\\s*\"cd565097-4b69-490e-b167-b59128475562\""),
        )
        assertContains(json, Regex("\"description\":\\s*\"stub\""))
        assertContains(json, Regex("\"currency\":\\s*\"RUB\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(request)
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
//        val obj = apiV2Mapper.decodeFromString(AdRequestSerializer, json) as AdCreateRequest
        val obj = apiV1Mapper.decodeFromString(json) as AccountCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj =
            apiV1Mapper.decodeFromString<AccountCreateRequest>(jsonString)

        assertEquals("123", obj.requestId)
    }
}
