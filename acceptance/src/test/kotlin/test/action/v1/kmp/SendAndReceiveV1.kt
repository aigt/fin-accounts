package aigt.finaccounts.blackbox.test.action.v1.kmp

import aigt.finaccounts.api.v1.kmp.models.IRequest
import aigt.finaccounts.api.v1.kmp.models.IResponse
import aigt.finaccounts.api.v1.kmp.requests.apiV1RequestSerialize
import aigt.finaccounts.api.v1.kmp.responses.apiV1ResponseDeserialize
import aigt.finaccounts.blackbox.fixture.client.Client
import mu.KotlinLogging


private val log = KotlinLogging.logger {}


suspend fun Client.sendAndReceive(path: String, request: IRequest): IResponse {
    val requestBody = apiV1RequestSerialize(request)
    log.info { "Send to v1/$path\n$requestBody" }

    val responseBody = sendAndReceive(
        version = "v1",
        path = path,
        request = requestBody,
    )
    log.info { "Received\n$responseBody" }

    return apiV1ResponseDeserialize(responseBody)
}
