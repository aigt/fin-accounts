package aigt.finaccounts.blackbox.test

import aigt.finaccounts.blackbox.docker.KtorDockerCompose
import aigt.finaccounts.blackbox.fixture.BaseFunSpec
import aigt.finaccounts.blackbox.fixture.client.RestClient
import aigt.finaccounts.blackbox.test.api.v1.jvm.testApiV1Jvm
import aigt.finaccounts.blackbox.test.api.v1.kmp.testApiV1Kmp


class RestAcceptanceTest : BaseFunSpec(
    dockerCompose = KtorDockerCompose,
    body = {
        val jvmApiPath = "api"
        val jvmRestClient = RestClient(
            dockerCompose = KtorDockerCompose,
            apiPath = jvmApiPath,
        )
        testApiV1Jvm(jvmRestClient, "rest $jvmApiPath")

        val kmpApiPath = "api-kmp"
        val kmpRestClient = RestClient(
            dockerCompose = KtorDockerCompose,
            apiPath = kmpApiPath,
        )
        testApiV1Kmp(kmpRestClient, "rest $kmpApiPath")
    },
)
