package aigt.finaccounts.blackbox.test.action.v1


import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountStatus
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateObject
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateRequest
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateResponse
import aigt.finaccounts.blackbox.fixture.client.Client
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import java.util.*


suspend fun Client.updateAccount(
    id: String?,
    description: String? = null,
    ownerId: UUID? = null,
    currency: String? = null,
    lock: String? = null,
    balance: Int? = null,
    status: AccountStatus? = null,
): AccountResponseObject = updateAccount(
    description = description,
    ownerId = ownerId,
    currency = currency,
    id = id,
    lock = lock,
    balance = balance,
    status = status,
) {
    it should haveSuccessResult
    it.account shouldBe accountStub
    it.account!!
}


suspend fun <T> Client.updateAccount(
    id: String?,
    description: String?,
    ownerId: UUID?,
    currency: String?,
    lock: String?,
    balance: Int?,
    status: AccountStatus?,
    block: (AccountUpdateResponse) -> T,
): T = withClue("updateAccountV1: $id") {

    val response = sendAndReceive(
        path = "account/update",
        request = AccountUpdateRequest(
            requestType = "update",
            debug = debug,
            account = AccountUpdateObject(
                description = description,
                ownerId = ownerId,
                currency = currency,
                id = id,
                lock = lock,
                balance = balance,
                status = status,
            ),
        ),
    ) as AccountUpdateResponse

    response.asClue(block)
}
