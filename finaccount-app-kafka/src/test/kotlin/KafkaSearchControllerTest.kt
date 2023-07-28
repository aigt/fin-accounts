package aigt.finaccounts.app.kafka

import aigt.finaccounts.api.v1.kmp.models.AccountSearchResponse
import aigt.finaccounts.app.kafka.fixture.getAccountSearchRequest
import aigt.finaccounts.app.kafka.fixture.receiveFromKafka
import org.junit.Test
import kotlin.test.assertEquals


class KafkaSearchControllerTest {

    @Test
    fun runKafka() {

        val config = AppKafkaConfig()
        val outputTopic = config.kafkaTopicOutV1Jvm

        val testResults = receiveFromKafka<AccountSearchResponse>(
            request = getAccountSearchRequest(),
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
            "10002000300040005001",
            testResults.result.accounts?.first()?.id,
        )
    }
}


