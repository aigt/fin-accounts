package aigt.finaccounts.app.kafka

import aigt.finaccounts.api.v1.kmp.models.AccountReadResponse
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateResponse
import aigt.finaccounts.app.kafka.fixture.getAccountReadRequest
import aigt.finaccounts.app.kafka.fixture.getAccountUpdateRequest
import aigt.finaccounts.app.kafka.fixture.runSUT
import org.junit.Test
import kotlin.test.assertEquals


class KafkaUpdateControllerTest {

    @Test
    fun runKafka() {

        val config = AppKafkaConfig()
        val outputTopic = config.kafkaTopicOutV1Jvm

        val testResults =
            runSUT<AccountUpdateResponse>(request = getAccountUpdateRequest())

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


