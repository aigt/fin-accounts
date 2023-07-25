package aigt.finaccounts.blackbox.test


import aigt.finaccounts.blackbox.docker.KafkaDockerCompose
import aigt.finaccounts.blackbox.fixture.BaseFunSpec
import aigt.finaccounts.blackbox.fixture.client.KafkaClient

class AccKafkaTest : BaseFunSpec(
    KafkaDockerCompose,
    {
        val client = KafkaClient(KafkaDockerCompose)

        testApiV1(client)
    },
)
