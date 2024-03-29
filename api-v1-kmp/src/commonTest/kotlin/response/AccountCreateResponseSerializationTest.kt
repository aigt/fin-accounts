package aigt.finaccounts.api.v1.kmp.response

import aigt.finaccounts.api.v1.kmp.models.AccountCreateResponse
import aigt.finaccounts.api.v1.kmp.models.AccountPermissions
import aigt.finaccounts.api.v1.kmp.models.AccountResponseObject
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.models.ResponseResult
import aigt.finaccounts.api.v1.kmp.responses.apiV1ResponseDeserialize
import aigt.finaccounts.api.v1.kmp.responses.apiV1ResponseSerialize
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AccountCreateResponseSerializationTest {

    private val response = AccountCreateResponse(
        responseType = "create",
        requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
        result = ResponseResult.SUCCESS,
        errors = listOf(),
        account = AccountResponseObject(
            description = "stub description",
            ownerId = "cd565097-4b69-490e-b167-b59128475562",
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
    )

    @Test
    fun serialize() {
        val json = apiV1ResponseSerialize(response)


        assertContains(
            json,
            Regex("\"responseType\":\\s*\"create\""),
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
        val obj = apiV1ResponseDeserialize<AccountCreateResponse>(json)

        assertEquals(response, obj)
    }
}
