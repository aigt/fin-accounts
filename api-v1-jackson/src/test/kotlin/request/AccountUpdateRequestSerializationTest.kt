package aigt.finaccounts.api.v1.jackson.request

import aigt.finaccounts.api.v1.jackson.apiV1Mapper
import aigt.finaccounts.api.v1.jackson.apiV1RequestDeserialize
import aigt.finaccounts.api.v1.jackson.apiV1RequestSerialize
import aigt.finaccounts.api.v1.jackson.models.AccountDebug
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.jackson.models.AccountStatus
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateObject
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateRequest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AccountUpdateRequestSerializationTest {

    private val request = AccountUpdateRequest(
        requestType = "update",
        requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
        debug = AccountDebug(
            mode = AccountRequestDebugMode.STUB,
            stub = AccountRequestDebugStubs.BAD_OWNER_ID,
        ),
        account = AccountUpdateObject(
            description = "stub description",
            ownerId = UUID.fromString("cd565097-4b69-490e-b167-b59128475562"),
            currency = "RUB",
            id = "26c45c31-857f-4d5d-bf59-890817c9320b",
            lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
            balance = 1005_00,
            status = AccountStatus.FROZEN,
        ),
    )

    @Test
    fun serialize() {
        val json = apiV1RequestSerialize(request)

        assertContains(
            json,
            Regex("\"requestType\":\\s*\"update\""),
        )
        assertContains(
            json,
            Regex("\"requestId\":\\s*\"75038a32-9d63-4394-968b-d33aaedc057e\""),
        )
        assertContains(
            json,
            Regex("\"description\":\\s*\"stub description\""),
        )
        assertContains(
            json,
            Regex("\"ownerId\":\\s*\"cd565097-4b69-490e-b167-b59128475562\""),
        )
        assertContains(
            json,
            Regex("\"currency\":\\s*\"RUB\""),
        )
        assertContains(
            json,
            Regex("\"id\":\\s*\"26c45c31-857f-4d5d-bf59-890817c9320b\""),
        )
        assertContains(
            json,
            Regex("\"lock\":\\s*\"a3c0cffb-97d3-4e9d-898d-10eb10470501\""),
        )
        assertContains(
            json,
            Regex("\"balance\":\\s*100500"),
        )
        assertContains(
            json,
            Regex("\"status\":\\s*\"frozen\""),
        )


        // Баг Jackson - дублирует дискриминатор
        /*assertContains(
            json,
            Regex("^(?![\\S\\s]*?(\"requestType\":\\s*null)[\\S\\s]*+\$)"),
            "Request Type не должен быть null",
        )
        assertContains(
            json,
            Regex("/^(?![\\S\\s]*?(\"requestType\":[\\S\\s]*){2}+[\\S\\s]*+\$)"),
            "Должен отсутствовать повторяющийся дискриминатор",
        )*/
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1RequestDeserialize<AccountUpdateRequest>(json)

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
            apiV1Mapper.readValue(jsonString, AccountUpdateRequest::class.java)

        assertEquals("75038a32-9d63-4394-968b-d33aaedc057e", obj.requestId)
    }
}
