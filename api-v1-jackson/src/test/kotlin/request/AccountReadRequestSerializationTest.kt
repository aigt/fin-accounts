package aigt.finaccounts.api.v1.jackson.request

import aigt.finaccounts.api.v1.jackson.apiV1Mapper
import aigt.finaccounts.api.v1.jackson.apiV1RequestDeserialize
import aigt.finaccounts.api.v1.jackson.apiV1RequestSerialize
import aigt.finaccounts.api.v1.jackson.models.AccountDebug
import aigt.finaccounts.api.v1.jackson.models.AccountReadObject
import aigt.finaccounts.api.v1.jackson.models.AccountReadRequest
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugStubs
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AccountReadRequestSerializationTest {

    private val request = AccountReadRequest(
        requestType = "read",
        requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
        debug = AccountDebug(
            mode = AccountRequestDebugMode.STUB,
            stub = AccountRequestDebugStubs.BAD_OWNER_ID,
        ),
        account = AccountReadObject(id = "26c45c31-857f-4d5d-bf59-890817c9320b"),
    )

    @Test
    fun serialize() {
        val json = apiV1RequestSerialize(request)

        assertContains(
            json,
            Regex("\"requestType\":\\s*\"read\""),
        )
        assertContains(
            json,
            Regex("\"requestId\":\\s*\"75038a32-9d63-4394-968b-d33aaedc057e\""),
        )
        assertContains(
            json,
            Regex("\"id\":\\s*\"26c45c31-857f-4d5d-bf59-890817c9320b\""),
        )

        // Баг Jackson - дублирует дискриминатор
        assertContains(
            json,
            Regex("^(?![\\S\\s]*?(\"requestType\":\\s*null)[\\S\\s]*+\$)"),
            "Request Type не должен быть null",
        )
        /*assertContains(
            json,
            Regex("/^(?![\\S\\s]*?(\"requestType\":[\\S\\s]*){2}+[\\S\\s]*+\$)"),
            "Должен отсутствовать повторяющийся дискриминатор",
        )*/
    }

    @Test
    fun deserialize() {
        val json = apiV1RequestSerialize(request)
        val obj = apiV1RequestDeserialize<AccountReadRequest>(json)

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {
                "requestId":"75038a32-9d63-4394-968b-d33aaedc057e"
            }
        """.trimIndent()
        val obj =
            apiV1Mapper.readValue(jsonString, AccountReadRequest::class.java)

        assertEquals("75038a32-9d63-4394-968b-d33aaedc057e", obj.requestId)
    }
}
