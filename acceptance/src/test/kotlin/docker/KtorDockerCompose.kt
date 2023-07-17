package aigt.finaccounts.blackbox.docker


object KtorDockerCompose : AbstractDockerCompose(
    service = "app-ktor_1",
    port = 8080,
    dockerComposeName = "ktor/docker-compose-ktor.yml",
)
