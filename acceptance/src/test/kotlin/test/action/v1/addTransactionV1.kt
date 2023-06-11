package aigt.finaccounts.blackbox.test.action.v1

import aigt.finaccounts.blackbox.fixture.client.Client
import io.kotest.assertions.withClue
import io.kotest.matchers.should

suspend fun Client.createAd(): Unit = withClue("addTransactionV1") {

    val response = sendAndReceive(
        path = "account/transaction",
        requestBody = """
            {
                "name": "Bolt"
            }
        """.trimIndent(),
    )

    response should haveNoErrors
}
