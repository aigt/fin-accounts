package aigt.finaccounts.blackbox.test

import aigt.finaccounts.blackbox.docker.KtorDockerCompose
import aigt.finaccounts.blackbox.fixture.BaseFunSpec
import aigt.finaccounts.blackbox.fixture.client.RestClient
import aigt.finaccounts.blackbox.test.api.v1.jvm.testApiV1Jvm
import aigt.finaccounts.blackbox.test.api.v1.kmp.testApiV1Kmp


class RestJvmAcceptanceTest : BaseFunSpec(
    dockerCompose = KtorDockerCompose,
    body = {
        val apiPath = "api"
        val restClient = RestClient(
            dockerCompose = KtorDockerCompose,
            apiPath = apiPath,
        )
        testApiV1Jvm(restClient, "rest $apiPath")
    },
)


class RestKmpAcceptanceTest : BaseFunSpec(
    dockerCompose = KtorDockerCompose,
    body = {
        val apiPath = "api-kmp"
        val restClient = RestClient(
            dockerCompose = KtorDockerCompose,
            apiPath = apiPath,
        )
        testApiV1Kmp(restClient, "rest $apiPath")
    },
)
