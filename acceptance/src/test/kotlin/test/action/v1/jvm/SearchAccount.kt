package aigt.finaccounts.blackbox.test.action.v1.jvm


import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountSearchFilter
import aigt.finaccounts.api.v1.jackson.models.AccountSearchRequest
import aigt.finaccounts.api.v1.jackson.models.AccountSearchResponse
import aigt.finaccounts.blackbox.fixture.client.Client
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should


suspend fun Client.searchAccount(
    accountFilter: AccountSearchFilter,
): List<AccountResponseObject> = searchAccount(accountFilter) {
    it should haveSuccessResult
    it.accounts ?: listOf()
}


suspend fun <T> Client.searchAccount(
    accountFilter: AccountSearchFilter,
    block: (AccountSearchResponse) -> T,
): T = withClue("searchAccountV1: $accountFilter") {

    val requestType = "search"
    val path = "account/$requestType"

    val request = AccountSearchRequest(
        requestType = requestType,
        debug = debug,
        accountFilter = accountFilter,
    )

    val response = sendAndReceive(
        path = path,
        request = request,
    ) as AccountSearchResponse

    response.asClue(block)
}
