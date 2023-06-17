package aigt.finaccounts.api.v1.kmp

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import aigt.finaccounts.api.v1.kmp.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response: IResponse = AccountCreateResponse(
        responseType = "create",
        requestId = "123",
        account = AccountResponseObject(
            ownerId = "cd565097-4b69-490e-b167-b59128475562",
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
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
        val json = apiV2Mapper.encodeToString(response)

        println(json)

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
        val json = apiV2Mapper.encodeToString(response)
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
//        val obj = apiV2Mapper.decodeFromString(AdRequestSerializer, json) as AdCreateRequest
        val obj = apiV2Mapper.decodeFromString(json) as AccountCreateResponse

        assertEquals(response, obj)
    }
}
