package aigt.finaccounts.blackbox.docker


object KafkaDockerCompose : AbstractDockerCompose(
    service = "kafka",
    port = 9091,
    dockerComposeName = "kafka/docker-compose-kafka.yml",
)
