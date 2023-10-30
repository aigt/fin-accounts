package aigt.finaccounts.blackbox.test.action.v1.jvm


import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountTransactObject
import aigt.finaccounts.api.v1.jackson.models.AccountTransactRequest
import aigt.finaccounts.api.v1.jackson.models.AccountTransactResponse
import aigt.finaccounts.api.v1.jackson.models.AccountTransactionCreate
import aigt.finaccounts.api.v1.jackson.models.TransactionType
import aigt.finaccounts.blackbox.fixture.client.Client
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe


suspend fun Client.transactAccount(
    accountId: String?,
    lock: String? = null,
    transactionType: TransactionType? = null,
    transactionAmount: Int? = null,
    transactionCounterparty: String? = null,
    transactionDescription: String? = null,
    transactionTimestamp: String? = null,
): AccountResponseObject = transactAccount(
    accountId = accountId,
    lock = lock,
    transactionType = transactionType,
    transactionAmount = transactionAmount,
    transactionCounterparty = transactionCounterparty,
    transactionDescription = transactionDescription,
) {
    it should haveSuccessResult
    it.account shouldBe accountStub
    it.history ?: listOf()
    it.account!!
}


suspend fun <T> Client.transactAccount(
    accountId: String?,
    lock: String? = null,
    transactionType: TransactionType? = null,
    transactionAmount: Int? = null,
    transactionCounterparty: String? = null,
    transactionDescription: String? = null,
    block: (AccountTransactResponse) -> T,
): T = withClue("transactAccountV1: $accountId") {

    val requestType = "transact"
    val path = "account/$requestType"

    val request = AccountTransactRequest(
        requestType = requestType,
        debug = debug,
        account = AccountTransactObject(id = accountId, lock = lock),
        transaction = AccountTransactionCreate(
            type = transactionType,
            amount = transactionAmount,
            counterparty = transactionCounterparty,
            description = transactionDescription,
        ),
    )

    val response = sendAndReceive(
        path = path,
        request = request,
    ) as AccountTransactResponse

    response.asClue(block)
}
