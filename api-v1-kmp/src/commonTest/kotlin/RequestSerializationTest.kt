package aigt.finaccounts.api.v1.kmp

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import aigt.finaccounts.api.v1.kmp.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request: IRequest = AccountCreateRequest(
        requestType = "create",
        requestId = "123",
        debug = AccountDebug(
            mode = AccountRequestDebugMode.STUB,
            stub = AccountRequestDebugStubs.BAD_OWNER_ID
        ),
        account = AccountCreateObject(
            ownerId = "cd565097-4b69-490e-b167-b59128475562",
            description = "stub",
            balance = 154,
            currency = "RUB",
            status = AccountStatus.ACTIVE,
        )
    )

    @Test
    fun serialize() {
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
        val json = apiV1Mapper.encodeToString(request)

        println(json)

        assertContains(json, Regex("\"ownerId\":\\s*\"cd565097-4b69-490e-b167-b59128475562\""))
        assertContains(json, Regex("\"description\":\\s*\"stub\""))
        assertContains(json, Regex("\"balance\":\\s*154"))
        assertContains(json, Regex("\"currency\":\\s*\"RUB\""))
        assertContains(json, Regex("\"status\":\\s*\"active\""))
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
        val obj = apiV1Mapper.decodeFromString<AccountCreateRequest>(jsonString)

        assertEquals("123", obj.requestId)
    }
}
