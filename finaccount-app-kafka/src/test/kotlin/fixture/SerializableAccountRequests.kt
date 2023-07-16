package aigt.finaccounts.app.kafka.fixture

import aigt.finaccounts.api.v1.kmp.models.AccountCreateObject
import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs


fun getAccountCreateRequest() = AccountCreateRequest(
    requestType = "create",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = AccountDebug(
        mode = AccountRequestDebugMode.STUB,
        stub = AccountRequestDebugStubs.SUCCESS,
    ),
    account = AccountCreateObject(
        description = "stub description",
        ownerId = "9deb6b8c-b797-4b34-9201-776ae1d3cf58",
        currency = "RUB",
    ),
)
