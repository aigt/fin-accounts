package aigt.finaccounts.api.v1.kmp.request

import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.api.v1.kmp.models.AccountCreateObject
import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestDeserialize
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestSerialize
import kotlinx.serialization.decodeFromString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AccountCreateRequestSerializationTest {

    private val request = AccountCreateRequest(
        requestType = "create",
        requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
        debug = AccountDebug(
            mode = AccountRequestDebugMode.STUB,
            stub = AccountRequestDebugStubs.BAD_OWNER_ID,
        ),
        account = AccountCreateObject(
            description = "stub description",
            ownerId = "cd565097-4b69-490e-b167-b59128475562",
            currency = "RUB",
        ),
    )

    @Test
    fun serialize() {
        val json = apiV1RequestSerialize(request)

        assertContains(
            json,
            Regex("\"requestType\":\\s*\"create\""),
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
        val obj = apiV1RequestDeserialize<AccountCreateRequest>(json)

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
            apiV1Mapper.decodeFromString<AccountCreateRequest>(jsonString)
        //  as AccountCreateRequest

        assertEquals("75038a32-9d63-4394-968b-d33aaedc057e", obj.requestId)
    }
}
