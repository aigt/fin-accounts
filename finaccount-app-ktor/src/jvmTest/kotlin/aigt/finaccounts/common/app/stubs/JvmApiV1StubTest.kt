package aigt.finaccounts.app.stubs

import aigt.finaccounts.api.v1.jackson.models.AccountCreateObject
import aigt.finaccounts.api.v1.jackson.models.AccountCreateRequest
import aigt.finaccounts.api.v1.jackson.models.AccountCreateResponse
import aigt.finaccounts.api.v1.jackson.models.AccountDebug
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryObject
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryResponse
import aigt.finaccounts.api.v1.jackson.models.AccountReadObject
import aigt.finaccounts.api.v1.jackson.models.AccountReadRequest
import aigt.finaccounts.api.v1.jackson.models.AccountReadResponse
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.jackson.models.AccountSearchFilter
import aigt.finaccounts.api.v1.jackson.models.AccountSearchRequest
import aigt.finaccounts.api.v1.jackson.models.AccountSearchResponse
import aigt.finaccounts.api.v1.jackson.models.AccountStatus
import aigt.finaccounts.api.v1.jackson.models.AccountTransactObject
import aigt.finaccounts.api.v1.jackson.models.AccountTransactRequest
import aigt.finaccounts.api.v1.jackson.models.AccountTransactResponse
import aigt.finaccounts.api.v1.jackson.models.AccountTransactionCreate
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateObject
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateRequest
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateResponse
import aigt.finaccounts.api.v1.jackson.models.TransactionType
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class JvmApiV1StubTest {

    val accountV1Url = "/api/v1/account"

    @Test
    fun create() = testApplication {
        val client = myClient()
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
                    ownerId = UUID.fromString("b32dbe3e-8b3e-40ee-b66f-4b7913b24fea"),
                    currency = "XYZ",
                ),
            )

            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }

        val responseObj = response.body<AccountCreateResponse>()

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005000", responseObj.account?.id)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()
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
            setBody(requestObject)
        }

        val responseObj = response.body<AccountReadResponse>()

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005000", responseObj.account?.id)
    }

    @Test
    fun update() = testApplication {
        val client = myClient()
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
                    ownerId = UUID.fromString("b32dbe3e-8b3e-40ee-b66f-4b7913b24fea"),
                    balance = 500_00,
                    currency = "XYZ",
                    status = AccountStatus.FROZEN,
                    id = "10002000300040005000",
                ),
            )

            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }

        val responseObj = response.body<AccountUpdateResponse>()

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005000", responseObj.account?.id)
    }

    @Test
    fun history() = testApplication {
        val client = myClient()
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
            setBody(requestObject)
        }

        val responseObj = response.body<AccountHistoryResponse>()

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005000", responseObj.account?.id)
        assertEquals(100_00, responseObj.history?.first()?.amount)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()
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
            setBody(requestObject)
        }

        val responseObj = response.body<AccountSearchResponse>()

        assertEquals(200, response.status.value)
        assertEquals("10002000300040005001", responseObj.accounts?.first()?.id)
    }

    @Test
    fun transact() = testApplication {
        val client = myClient()
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
                transaction = AccountTransactionCreate(
                    type = TransactionType.INCOME,
                    amount = 100_00,
                    counterparty = "3f275212-fe2e-4109-953c-a0b68acde6b9",
                    description = "Add new transaction for test",
                ),
            )

            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }

        val responseObj = response.body<AccountTransactResponse>()

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

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}
