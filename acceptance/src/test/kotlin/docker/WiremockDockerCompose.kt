package aigt.finaccounts.blackbox.docker


object WiremockDockerCompose : AbstractDockerCompose(
    service = "app-wiremock_1",
    port = 8080,
    dockerComposeName = "wiremock/docker-compose-wiremock.yml",
)
