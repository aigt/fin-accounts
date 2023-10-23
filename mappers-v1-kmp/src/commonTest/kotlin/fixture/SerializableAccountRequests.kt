package aigt.finaccounts.mappers.kmp.v1.fixture

import aigt.finaccounts.api.v1.kmp.models.AccountCreateObject
import aigt.finaccounts.api.v1.kmp.models.AccountCreateRequest
import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryObject
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.kmp.models.AccountReadObject
import aigt.finaccounts.api.v1.kmp.models.AccountReadRequest
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.kmp.models.AccountSearchFilter
import aigt.finaccounts.api.v1.kmp.models.AccountSearchRequest
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.models.AccountTransactObject
import aigt.finaccounts.api.v1.kmp.models.AccountTransactRequest
import aigt.finaccounts.api.v1.kmp.models.AccountTransactionCreate
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateObject
import aigt.finaccounts.api.v1.kmp.models.AccountUpdateRequest
import aigt.finaccounts.api.v1.kmp.models.TransactionType


fun getStubSuccessAccountDebug() = AccountDebug(
    mode = AccountRequestDebugMode.STUB,
    stub = AccountRequestDebugStubs.SUCCESS,
)


fun getAccountCreateRequest() = AccountCreateRequest(
    requestType = "create",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = getStubSuccessAccountDebug(),
    account = AccountCreateObject(
        description = "stub description",
        ownerId = "9deb6b8c-b797-4b34-9201-776ae1d3cf58",
        currency = "RUB",
    ),
)

fun getAccountReadRequest() = AccountReadRequest(
    requestType = "read",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = getStubSuccessAccountDebug(),
    account = AccountReadObject(id = "26c45c31-857f-4d5d-bf59-890817c9320b"),
)

fun getAccountUpdateRequest() = AccountUpdateRequest(
    requestType = "update",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = getStubSuccessAccountDebug(),
    account = AccountUpdateObject(
        description = "stub description",
        ownerId = "cd565097-4b69-490e-b167-b59128475562",
        currency = "RUB",
        id = "26c45c31-857f-4d5d-bf59-890817c9320b",
        lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
        balance = 1005_00,
        status = AccountStatus.FROZEN,
    ),
)

fun getAccountSearchRequest() = AccountSearchRequest(
    requestType = "search",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = getStubSuccessAccountDebug(),
    accountFilter = AccountSearchFilter(
        searchString = "stub search string",
        ownerId = "cd565097-4b69-490e-b167-b59128475562",
    ),
)

fun getAccountHistoryRequest() = AccountHistoryRequest(
    requestType = "history",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = getStubSuccessAccountDebug(),
    account = AccountHistoryObject(id = "26c45c31-857f-4d5d-bf59-890817c9320b"),
)

fun getAccountTransactRequest() = AccountTransactRequest(
    requestType = "transact",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = getStubSuccessAccountDebug(),
    account = AccountTransactObject(
        id = "26c45c31-857f-4d5d-bf59-890817c9320b",
        lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
    ),
    transaction = AccountTransactionCreate(
        type = TransactionType.WITHDRAW,
        amount = 1005_00,
        counterparty = "11102220333044405550",
        description = "stub transaction description",
    ),
)
