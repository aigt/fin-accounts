package aigt.finaccounts.app.kafka

import aigt.finaccounts.api.v1.kmp.models.AccountTransactResponse
import aigt.finaccounts.app.kafka.fixture.getAccountTransactRequest
import aigt.finaccounts.app.kafka.fixture.receiveFromKafka
import org.junit.Test
import kotlin.test.assertEquals


class KafkaTransactControllerTest {

    @Test
    fun runKafka() {

        val config = AppKafkaConfig()
        val outputTopic = config.kafkaTopicOutV1Jvm

        val testResults = receiveFromKafka<AccountTransactResponse>(
            request = getAccountTransactRequest(),
            config = config,
        )

        assertEquals(
            outputTopic,
            testResults.topic,
        )
        assertEquals(
            "75038a32-9d63-4394-968b-d33aaedc057e",
            testResults.result.requestId,
        )
        assertEquals(
            "9deb6b8c-b797-4b34-9201-776ae1d3cf58",
            testResults.result.account?.ownerId,
        )
    }
}


