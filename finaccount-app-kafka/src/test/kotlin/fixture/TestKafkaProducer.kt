package aigt.finaccounts.app.kafka.fixture

import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.serialization.StringSerializer

fun getTestKafkaProducer() = MockProducer<String, String>(
    true,
    StringSerializer(),
    StringSerializer(),
)
