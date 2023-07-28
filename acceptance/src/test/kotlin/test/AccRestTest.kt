package aigt.finaccounts.blackbox.test

import aigt.finaccounts.blackbox.docker.KtorDockerCompose
import aigt.finaccounts.blackbox.fixture.BaseFunSpec
import aigt.finaccounts.blackbox.fixture.client.RestClient
import aigt.finaccounts.blackbox.fixture.docker.DockerCompose
import io.kotest.core.annotation.Ignored


@Ignored
open class AccRestTestBase(
    dockerCompose: DockerCompose,
) : BaseFunSpec(
    dockerCompose = dockerCompose,
    body = {
        val restClient = RestClient(dockerCompose)
        testApiV1(restClient, "rest ")
    },
)

class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
