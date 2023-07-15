package aigt.finaccounts.api.v1.kmp.request

import aigt.finaccounts.api.v1.kmp.apiV1Mapper
import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.kmp.models.AccountTransactObject
import aigt.finaccounts.api.v1.kmp.models.AccountTransactRequest
import aigt.finaccounts.api.v1.kmp.models.AccountTransaction
import aigt.finaccounts.api.v1.kmp.models.TransactionType
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestDeserialize
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestSerialize
import kotlinx.serialization.decodeFromString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AccountTransactRequestSerializationTest {

    private val request = AccountTransactRequest(
        requestType = "transact",
        requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
        debug = AccountDebug(
            mode = AccountRequestDebugMode.STUB,
            stub = AccountRequestDebugStubs.BAD_OWNER_ID,
        ),
        account = AccountTransactObject(
            id = "26c45c31-857f-4d5d-bf59-890817c9320b",
            lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
        ),
        transaction = AccountTransaction(
            type = TransactionType.WITHDRAW,
            amount = 1005_00,
            counterparty = "11102220333044405550",
            description = "stub transaction description",
            timestamp = "2023-07-04T18:43:00.123456789Z",
        ),
    )

    @Test
    fun serialize() {
        val json = apiV1RequestSerialize(request)

        assertContains(
            json,
            Regex("\"requestType\":\\s*\"transact\""),
        )
        assertContains(
            json,
            Regex("\"requestId\":\\s*\"75038a32-9d63-4394-968b-d33aaedc057e\""),
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
            Regex("\"type\":\\s*\"withdraw\""),
        )
        assertContains(
            json,
            Regex("\"amount\":\\s*100500"),
        )
        assertContains(
            json,
            Regex("\"counterparty\":\\s*\"11102220333044405550\""),
        )
        assertContains(
            json,
            Regex("\"description\":\\s*\"stub transaction description\""),
        )
        assertContains(
            json,
            Regex("\"timestamp\":\\s*\"2023-07-04T18:43:00.123456789Z\""),
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
        val obj = apiV1RequestDeserialize<AccountTransactRequest>(json)

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
            apiV1Mapper.decodeFromString<AccountTransactRequest>(jsonString)
        //  as AccountTransactRequest

        assertEquals("75038a32-9d63-4394-968b-d33aaedc057e", obj.requestId)
    }
}
