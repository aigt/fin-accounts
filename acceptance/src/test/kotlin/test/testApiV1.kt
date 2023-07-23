package aigt.finaccounts.blackbox.test

import aigt.finaccounts.blackbox.fixture.client.Client
import aigt.finaccounts.blackbox.test.action.v1.createAccount
import aigt.finaccounts.blackbox.test.action.v1.readAccount
import aigt.finaccounts.blackbox.test.action.v1.searchAccount
import aigt.finaccounts.blackbox.test.action.v1.someCreateAccount
import aigt.finaccounts.blackbox.test.action.v1.updateAccount
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe


fun FunSpec.testApiV1(client: Client, prefix: String = "") {
    context("${prefix}v1") {

        test("Create Account ok") {
            client.createAccount()
        }

        test("Read Account ok") {
            val created = client.createAccount()
            client.readAccount(created.id).asClue {
                it shouldBe created
            }
        }

        test("Update Account ok") {
            val created = client.createAccount()
            client.updateAccount(
                created.id,
                description = "updated description",
            )
            client.readAccount(created.id) {
                // TODO раскомментировать, когда будет реальный реп
                // it.account?.description shouldBe "updated description"
            }
        }

        test("Search Account ok") {
            val created1 = client.createAccount()
            val created2 = client.createAccount(
                someCreateAccount.copy(description = "other description"),
            )

            withClue("Search Selling") {
                val results = client.searchAccount()
                // TODO раскомментировать, когда будет реальный реп
            }
        }
    }
}
