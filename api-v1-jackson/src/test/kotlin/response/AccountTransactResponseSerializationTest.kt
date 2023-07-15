package aigt.finaccounts.api.v1.jackson.response

import aigt.finaccounts.api.v1.jackson.apiV1ResponseDeserialize
import aigt.finaccounts.api.v1.jackson.apiV1ResponseSerialize
import aigt.finaccounts.api.v1.jackson.models.AccountPermissions
import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountStatus
import aigt.finaccounts.api.v1.jackson.models.AccountTransactResponse
import aigt.finaccounts.api.v1.jackson.models.AccountTransaction
import aigt.finaccounts.api.v1.jackson.models.ResponseResult
import aigt.finaccounts.api.v1.jackson.models.TransactionType
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AccountTransactResponseSerializationTest {

    private val response = AccountTransactResponse(
        responseType = "transact",
        requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
        result = ResponseResult.SUCCESS,
        errors = listOf(),
        account = AccountResponseObject(
            description = "stub description",
            ownerId = UUID.fromString("cd565097-4b69-490e-b167-b59128475562"),
            currency = "RUB",
            id = "26c45c31-857f-4d5d-bf59-890817c9320b",
            lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
            lastTransaction = "2023-07-04T18:43:00.123456789Z",
            permissions = setOf(
                AccountPermissions.UPDATE,
                AccountPermissions.READ,
            ),
            balance = 1005_00,
            status = AccountStatus.FROZEN,
        ),
        history = listOf(
            AccountTransaction(
                type = TransactionType.WITHDRAW,
                amount = 1005_00,
                counterparty = "11102220333044405550",
                description = "stub transaction description",
                timestamp = "2023-07-04T18:43:00.123456789Z",
            ),
        ),
    )

    @Test
    fun serialize() {
        val json = apiV1ResponseSerialize(response)


        assertContains(
            json,
            Regex("\"responseType\":\\s*\"transact\""),
        )
        assertContains(
            json,
            Regex("\"requestId\":\\s*\"75038a32-9d63-4394-968b-d33aaedc057e\""),
        )
        assertContains(
            json,
            Regex("\"result\":\\s*\"success\""),
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
            Regex("\"lastTransaction\":\\s*\"2023-07-04T18:43:00.123456789Z\""),
        )
        assertContains(
            json,
            Regex("\"permissions\":\\s*\\[\"update\",\"read\"\\]"),
        )
        assertContains(
            json,
            Regex("\"balance\":\\s*100500"),
        )
        assertContains(
            json,
            Regex("\"status\":\\s*\"frozen\""),
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
            Regex("^(?![\\S\\s]*?(\"responseType\":\\s*null)[\\S\\s]*+\$)"),
            "Request Type не должен быть null",
        )
        /*assertContains(
            json,
            Regex("/^(?![\\S\\s]*?(\"responseType\":[\\S\\s]*){2}+[\\S\\s]*+\$)"),
            "Должен отсутствовать повторяющийся дискриминатор",
        )*/
    }

    @Test
    fun deserialize() {
        val json = apiV1ResponseSerialize(response)
        val obj = apiV1ResponseDeserialize<AccountTransactResponse>(json)

        assertEquals(response, obj)
    }
}
