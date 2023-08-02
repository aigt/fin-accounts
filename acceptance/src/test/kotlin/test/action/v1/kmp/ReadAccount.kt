package aigt.finaccounts.blackbox.test.action.v1.kmp


import aigt.finaccounts.api.v1.kmp.models.AccountReadObject
import aigt.finaccounts.api.v1.kmp.models.AccountReadRequest
import aigt.finaccounts.api.v1.kmp.models.AccountReadResponse
import aigt.finaccounts.api.v1.kmp.models.AccountResponseObject
import aigt.finaccounts.blackbox.fixture.client.Client
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe


suspend fun Client.readAccount(
    id: String?,
): AccountResponseObject = readAccount(id) {
    it should haveSuccessResult
    it.account shouldBe accountStub
    it.account!!
}


suspend fun <T> Client.readAccount(
    id: String?,
    block: (AccountReadResponse) -> T,
): T = withClue("readAccountV1: $id") {

    val response = sendAndReceive(
        path = "account/read",
        request = AccountReadRequest(
            requestType = "read",
            debug = debug,
            account = AccountReadObject(id = id),
        ),
    ) as AccountReadResponse

    response.asClue(block)
}
