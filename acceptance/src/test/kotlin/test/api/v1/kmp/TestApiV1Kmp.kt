package aigt.finaccounts.blackbox.test.api.v1.kmp

import aigt.finaccounts.blackbox.fixture.client.Client
import aigt.finaccounts.blackbox.test.action.v1.kmp.createAccount
import aigt.finaccounts.blackbox.test.action.v1.kmp.readAccount
import aigt.finaccounts.blackbox.test.action.v1.kmp.transactAccount
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe


fun FunSpec.testApiV1Kmp(client: Client, prefix: String = "") {
    context("${prefix} v1") {

        test("Create Account ok") {
            client.createAccount()
        }

        test("Read Account ok") {
            val created = client.createAccount()
            client.readAccount(created.id).asClue {
                it shouldBe created
            }
        }

        /* Раскомментировать когда будет БД
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

        context("Search Account ok") {
            test("Search by ownerId") {
                val created1 = client.createAccount()
                val created2 = client.createAccount(
                    someCreateAccount.copy(description = "other description"),
                )

                val accountFilter = AccountSearchFilter(
                    ownerId = "cd565097-4b69-490e-b167-b59128475562",
                )

                withClue("Search Selling") {
                    val results = client.searchAccount(accountFilter)
                    // TODO раскомментировать, когда будет реальный реп
                }
            }

            test("Search by searchString") {
                val created1 = client.createAccount()
                val created2 = client.createAccount(
                    someCreateAccount.copy(description = "other description"),
                )

                val accountFilter = AccountSearchFilter(
                    searchString = "other description",
                )

                withClue("Search Selling") {
                    val results = client.searchAccount(accountFilter)
                    // TODO раскомментировать, когда будет реальный реп
                }
            }
        }
*/
        test("Transact Account ok") {
            val created = client.createAccount()

            withClue("Search Selling") {
                val results = client.transactAccount(created.id)
                // TODO раскомментировать, когда будет реальный реп
            }
        }
    }
}
