package aigt.finaccounts.common.app.stubs

import aigt.finaccounts.api.v1.kmp.models.AccountCreateObject
import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountCreateResponse
import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestSerialize
import aigt.finaccounts.api.v1.kmp.responses.apiV1ResponseDeserialize
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class CommonApiV1StubTest {

    @Test
    fun create() = testApplication {
        val response = client.post("/api-kmp/v1/account/create") {
            val requestObject = AccountCreateRequest(
                requestType = "create",
                requestId = "bdd12ca6-5812-4293-b93a-1a7b80767c66",
                debug = AccountDebug(
                    mode = AccountRequestDebugMode.STUB,
                    stub = AccountRequestDebugStubs.SUCCESS,
                ),
                account = AccountCreateObject(
                    description = "created for tests account",
                    ownerId = "b32dbe3e-8b3e-40ee-b66f-4b7913b24fea",
                    balance = 500_00,
                    currency = "RUB",
                    status = AccountStatus.ACTIVE,
                ),
            )

            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObject)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiV1ResponseDeserialize<AccountCreateResponse>(
            json = responseJson,
        )

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005000", responseObj.account?.id)
    }
}
