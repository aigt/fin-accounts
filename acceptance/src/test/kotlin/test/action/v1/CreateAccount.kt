package aigt.finaccounts.blackbox.test.action.v1


import aigt.finaccounts.api.v1.jackson.models.AccountCreateObject
import aigt.finaccounts.api.v1.jackson.models.AccountCreateRequest
import aigt.finaccounts.api.v1.jackson.models.AccountCreateResponse
import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.blackbox.fixture.client.Client
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe


suspend fun Client.createAccount(
    ad: AccountCreateObject = someCreateAccount,
): AccountResponseObject = createAccount(ad) {
    it should haveSuccessResult
    it.account shouldBe accountStub
    it.account!!
}


suspend fun <T> Client.createAccount(
    account: AccountCreateObject = someCreateAccount,
    block: (AccountCreateResponse) -> T,
): T = withClue("createAccountV1: $account") {

    val response = sendAndReceive(
        path = "account/create",
        request = AccountCreateRequest(
            requestType = "create", debug = debug, account = account,
        ),
    ) as AccountCreateResponse

    response.asClue(block)
}
