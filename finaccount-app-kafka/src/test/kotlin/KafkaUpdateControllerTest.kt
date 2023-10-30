package aigt.finaccounts.app.kafka

import aigt.finaccounts.api.v1.kmp.models.AccountUpdateResponse
import aigt.finaccounts.app.kafka.fixture.getAccountUpdateRequest
import aigt.finaccounts.app.kafka.fixture.receiveFromKafka
import org.junit.Test
import kotlin.test.assertEquals


class KafkaUpdateControllerTest {

    @Test
    fun runKafka() {

        val config = AppKafkaConfig()
        val outputTopic = config.kafkaTopicOutV1Jvm

        val testResults = receiveFromKafka<AccountUpdateResponse>(
            request = getAccountUpdateRequest(),
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
            "cd565097-4b69-490e-b167-b59128475562",
            testResults.result.account?.ownerId,
        )
    }
}


