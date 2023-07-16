package aigt.finaccounts.app.kafka.fixture

import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.models.IResponse
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestSerialize
import aigt.finaccounts.api.v1.kmp.responses.apiV1ResponseDeserialize
import aigt.finaccounts.app.kafka.AppKafkaConfig

data class TestResults<R : IResponse>(
    val message: String,
    val topic: String,
    val result: R,
)

fun <R : IResponse> runSUT(request: IRequest): TestResults<R> {

    val config = AppKafkaConfig()
    val producer = getTestKafkaProducer()
    val json = apiV1RequestSerialize(request)
    val app = TestKafkaAppBuilder().build {
        this.config = config
        this.producer = producer
        this.recordValue = json
    }

    app.run()

    val message = producer.history()
        .first()

    val result = apiV1ResponseDeserialize<R>(message.value())

    return TestResults<R>(
        message = message.value(),
        topic = message.topic(),
        result = result,
    )
}
