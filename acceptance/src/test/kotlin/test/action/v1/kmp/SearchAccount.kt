package aigt.finaccounts.blackbox.test.action.v1.kmp


import aigt.finaccounts.api.v1.kmp.models.AccountResponseObject
import aigt.finaccounts.api.v1.kmp.models.AccountSearchFilter
import aigt.finaccounts.api.v1.kmp.models.AccountSearchRequest
import aigt.finaccounts.api.v1.kmp.models.AccountSearchResponse
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
