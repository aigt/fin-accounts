package aigt.finaccounts.api.v1.jackson

import aigt.finaccounts.api.v1.jackson.models.AccountCreateObject
import aigt.finaccounts.api.v1.jackson.models.AccountCreateRequest
import aigt.finaccounts.api.v1.jackson.models.AccountDebug
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.jackson.models.IRequest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = AccountCreateRequest(
        requestId = "123",
        //requestType = "create",
        debug = AccountDebug(
            mode = AccountRequestDebugMode.STUB,
            stub = AccountRequestDebugStubs.BAD_OWNER_ID,
        ),
        account = AccountCreateObject(
            ownerId = UUID.fromString("cd565097-4b69-490e-b167-b59128475562"),
            description = "stub",
            currency = "RUB",
        ),
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

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
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(
            json,
            IRequest::class.java,
        ) as AccountCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj =
            apiV1Mapper.readValue(jsonString, AccountCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}
