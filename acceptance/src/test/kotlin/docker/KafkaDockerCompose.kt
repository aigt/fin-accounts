package aigt.finaccounts.blackbox.docker


object KafkaDockerCompose : AbstractDockerCompose(
    service = "kafka_1",
    port = 9091,
    dockerComposeName = "kafka/docker-compose-kafka.yml",
)
