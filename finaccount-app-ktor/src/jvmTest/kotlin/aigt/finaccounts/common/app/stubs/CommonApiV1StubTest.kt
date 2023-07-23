package aigt.finaccounts.app.stubs

import aigt.finaccounts.api.v1.kmp.models.AccountCreateObject
import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountCreateResponse
import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryObject
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryResponse
import aigt.finaccounts.api.v1.kmp.models.AccountReadObject
import aigt.finaccounts.api.v1.kmp.models.AccountReadRequest
import aigt.finaccounts.api.v1.kmp.models.AccountReadResponse
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.kmp.models.AccountSearchFilter
import aigt.finaccounts.api.v1.kmp.models.AccountSearchRequest
import aigt.finaccounts.api.v1.kmp.models.AccountSearchResponse
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.models.AccountTransactObject
import aigt.finaccounts.api.v1.kmp.models.AccountTransactRequest
import aigt.finaccounts.api.v1.kmp.models.AccountTransactResponse
import aigt.finaccounts.api.v1.kmp.models.AccountTransaction
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateObject
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateResponse
import aigt.finaccounts.api.v1.kmp.models.TransactionType
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestSerialize
import aigt.finaccounts.api.v1.kmp.responses.apiV1ResponseDeserialize
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class CommonApiV1StubTest {

    val accountV1Url = "/api-kmp/v1/account"

    @Test
    fun create() = testApplication {
        val response = client.post("${accountV1Url}/create") {
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
                    currency = "XYZ",
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

    @Test
    fun read() = testApplication {
        val response = client.post("${accountV1Url}/read") {
            val requestObject = AccountReadRequest(
                requestType = "read",
                requestId = "bdd12ca6-5812-4293-b93a-1a7b80767c66",
                debug = AccountDebug(
                    mode = AccountRequestDebugMode.STUB,
                    stub = AccountRequestDebugStubs.SUCCESS,
                ),
                account = AccountReadObject(id = "10002000300040005000"),
            )

            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObject)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiV1ResponseDeserialize<AccountReadResponse>(
            json = responseJson,
        )

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005000", responseObj.account?.id)
    }

    @Test
    fun update() = testApplication {
        val response = client.post("${accountV1Url}/update") {
            val requestObject = AccountUpdateRequest(
                requestType = "update",
                requestId = "bdd12ca6-5812-4293-b93a-1a7b80767c66",
                debug = AccountDebug(
                    mode = AccountRequestDebugMode.STUB,
                    stub = AccountRequestDebugStubs.SUCCESS,
                ),
                account = AccountUpdateObject(
                    description = "created for tests account",
                    ownerId = "b32dbe3e-8b3e-40ee-b66f-4b7913b24fea",
                    balance = 500_00,
                    currency = "XYZ",
                    status = AccountStatus.FROZEN,
                    id = "10002000300040005000",
                ),
            )

            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObject)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiV1ResponseDeserialize<AccountUpdateResponse>(
            json = responseJson,
        )

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005000", responseObj.account?.id)
        /*assertEquals(
            "b32dbe3e-8b3e-40ee-b66f-4b7913b24fea",
            responseObj.account?.ownerId,
        )
        assertEquals(500_00, responseObj.account?.balance)
        assertEquals(
            "created for tests account",
            responseObj.account?.description,
        )
        assertEquals("XYZ", responseObj.account?.currency)
        assertEquals(AccountStatus.FROZEN, responseObj.account?.status)*/
    }

    @Test
    fun history() = testApplication {
        val response = client.post("${accountV1Url}/history") {
            val requestObject = AccountHistoryRequest(
                requestType = "history",
                requestId = "bdd12ca6-5812-4293-b93a-1a7b80767c66",
                debug = AccountDebug(
                    mode = AccountRequestDebugMode.STUB,
                    stub = AccountRequestDebugStubs.SUCCESS,
                ),
                account = AccountHistoryObject(id = "10002000300040005000"),
            )

            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObject)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiV1ResponseDeserialize<AccountHistoryResponse>(
            json = responseJson,
        )

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005000", responseObj.account?.id)
        assertEquals(100_00, responseObj.history?.first()?.amount)
    }

    @Test
    fun search() = testApplication {
        val response = client.post("${accountV1Url}/search") {
            val requestObject = AccountSearchRequest(
                requestType = "search",
                requestId = "bdd12ca6-5812-4293-b93a-1a7b80767c66",
                debug = AccountDebug(
                    mode = AccountRequestDebugMode.STUB,
                    stub = AccountRequestDebugStubs.SUCCESS,
                ),
                accountFilter = AccountSearchFilter(
                    searchString = null,
                    ownerId = null,
                ),
            )

            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObject)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiV1ResponseDeserialize<AccountSearchResponse>(
            json = responseJson,
        )

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005001", responseObj.accounts?.first()?.id)
    }

    @Test
    fun transact() = testApplication {
        val response = client.post("${accountV1Url}/transact") {
            val requestObject = AccountTransactRequest(
                requestType = "transact",
                requestId = "bdd12ca6-5812-4293-b93a-1a7b80767c66",
                debug = AccountDebug(
                    mode = AccountRequestDebugMode.STUB,
                    stub = AccountRequestDebugStubs.SUCCESS,
                ),
                account = AccountTransactObject(
                    id = "10002000300040005000",
                    lock = null,
                ),
                transaction = AccountTransaction(
                    type = TransactionType.INCOME,
                    amount = 100_00,
                    counterparty = "3f275212-fe2e-4109-953c-a0b68acde6b9",
                    description = "Add new transaction for test",
                    timestamp = "2023-07-04T18:43:00.123456789Z",
                ),
            )

            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObject)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        println("responseJson = $responseJson")
        val responseObj = apiV1ResponseDeserialize<AccountTransactResponse>(
            json = responseJson,
        )

        assertEquals(
            200,
            response.status.value,
        )
        assertEquals(
            "10002000300040005000",
            responseObj.account?.id,
        )
        assertEquals(
            "3f275212-fe2e-4109-953c-a0b68acde6b9",
            responseObj.history?.first()?.counterparty,
        )
    }
}
