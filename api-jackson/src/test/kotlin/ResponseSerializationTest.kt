package aigt.api.jackson.v1

import aigt.api.jackson.v1.models.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = AccountCreateResponse(
        requestId = "123",
        account = AccountResponseObject(
            ownerId = UUID.fromString("cd565097-4b69-490e-b167-b59128475562"),
            description = "stub",
            balance = 154,
            currency = "RUB",
            status = AccountStatus.ACTIVE,
            id = "94852616476317587179",
            lock = "234",
            lastTransaction = "2023-06-12T16:55:08.219Z",
            permissions = setOf(AccountPermissions.UPDATE, AccountPermissions.READ),
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"ownerId\":\\s*\"cd565097-4b69-490e-b167-b59128475562\""))
        assertContains(json, Regex("\"description\":\\s*\"stub\""))
        assertContains(json, Regex("\"balance\":\\s*154"))
        assertContains(json, Regex("\"currency\":\\s*\"RUB\""))
        assertContains(json, Regex("\"status\":\\s*\"active\""))
        assertContains(json, Regex("\"id\":\\s*\"94852616476317587179\""))
        assertContains(json, Regex("\"lock\":\\s*\"234\""))
        assertContains(json, Regex("\"lastTransaction\":\\s*\"2023-06-12T16:55:08.219Z\""))
        assertContains(json, Regex("\"permissions\":\\s*\\[\"update\",\"read\"\\]"))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as AccountCreateResponse

        assertEquals(response, obj)
    }
}
