package aigt.finaccounts.blackbox.test.action.v1

import aigt.finaccounts.api.v1.jackson.apiV1RequestSerialize
import aigt.finaccounts.api.v1.jackson.apiV1ResponseDeserialize
import aigt.finaccounts.api.v1.jackson.models.IRequest
import aigt.finaccounts.api.v1.jackson.models.IResponse
import aigt.finaccounts.blackbox.fixture.client.Client
import mu.KotlinLogging


private val log = KotlinLogging.logger {}


suspend fun Client.sendAndReceive(path: String, request: IRequest): IResponse {
    val requestBody = apiV1RequestSerialize(request)
    log.info { "Send to v1/$path\n$requestBody" }

    val responseBody = sendAndReceive("v1", path, requestBody)
    log.info { "Received\n$responseBody" }

    return apiV1ResponseDeserialize(responseBody)
}
