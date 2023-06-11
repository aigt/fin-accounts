package aigt.finaccounts.blackbox.test

import aigt.finaccounts.blackbox.fixture.client.Client
import aigt.finaccounts.blackbox.test.action.v1.createAd
import io.kotest.core.spec.style.FunSpec

fun FunSpec.testApiV1(client: Client) {
    context("v1") {
        test("Add Transaction ok") {
            client.createAd()
        }
    }
}
