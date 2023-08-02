package aigt.finaccounts.blackbox.test


import aigt.finaccounts.blackbox.docker.KafkaDockerCompose
import aigt.finaccounts.blackbox.fixture.BaseFunSpec
import aigt.finaccounts.blackbox.fixture.client.KafkaClient
import aigt.finaccounts.blackbox.test.api.v1.jvm.testApiV1Jvm
import aigt.finaccounts.blackbox.test.api.v1.kmp.testApiV1Kmp


class KafkaJvmAcceptanceTest : BaseFunSpec(
    dockerCompose = KafkaDockerCompose,
    body = {
        val jvmApiPath = "jvm"
        val jvmClient = KafkaClient(
            dockerCompose = KafkaDockerCompose,
            apiPath = jvmApiPath,
        )
        testApiV1Jvm(jvmClient, jvmApiPath)

        val kmpApiPath = "kmp"
        val kmpClient = KafkaClient(
            dockerCompose = KafkaDockerCompose,
            apiPath = kmpApiPath,
        )
        testApiV1Kmp(kmpClient, kmpApiPath)
    },
)

//
// class KafkaKmpAcceptanceTest : BaseFunSpec(
//     dockerCompose = KafkaDockerCompose,
//     body = {
//         val client = KafkaClient(
//             dockerCompose = KafkaDockerCompose,
//             apiPath = "kmp",
//         )
//         testApiV1Kmp(client, "kmp")
//     },
// )
