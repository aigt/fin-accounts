package aigt.finaccounts.blackbox.test.action.v1

import aigt.finaccounts.api.v1.jackson.models.AccountCreateObject
import aigt.finaccounts.api.v1.jackson.models.AccountDebug
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryObject
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryRequest
import aigt.finaccounts.api.v1.jackson.models.AccountPermissions
import aigt.finaccounts.api.v1.jackson.models.AccountReadObject
import aigt.finaccounts.api.v1.jackson.models.AccountReadRequest
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountSearchFilter
import aigt.finaccounts.api.v1.jackson.models.AccountSearchRequest
import aigt.finaccounts.api.v1.jackson.models.AccountStatus
import aigt.finaccounts.api.v1.jackson.models.AccountTransactObject
import aigt.finaccounts.api.v1.jackson.models.AccountTransactRequest
import aigt.finaccounts.api.v1.jackson.models.AccountTransaction
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateObject
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateRequest
import aigt.finaccounts.api.v1.jackson.models.TransactionType
import java.util.UUID.fromString


val debug = AccountDebug(
    mode = AccountRequestDebugMode.STUB,
    stub = AccountRequestDebugStubs.SUCCESS,
)


val adStub = AccountResponseObject(
    description = "stub account response object description",
    ownerId = fromString("cd565097-4b69-490e-b167-b59128475562"),
    currency = "RUB",
    id = "26c45c31-857f-4d5d-bf59-890817c9320b",
    lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
    lastTransaction = "2023-07-04T18:43:00.123456789Z",
    permissions = setOf(
        AccountPermissions.UPDATE,
        AccountPermissions.READ,
    ),
    balance = 1005_00,
    status = AccountStatus.FROZEN,
)


val someCreateAccount = AccountCreateObject(
    description = "stub description",
    ownerId = fromString("cd565097-4b69-490e-b167-b59128475562"),
    currency = "RUB",
)


val someReadAccount = AccountReadRequest(
    requestType = "read",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = AccountDebug(
        mode = AccountRequestDebugMode.STUB,
        stub = AccountRequestDebugStubs.BAD_OWNER_ID,
    ),
    account = AccountReadObject(id = "26c45c31-857f-4d5d-bf59-890817c9320b"),
)


val someUpdateAccount = AccountUpdateRequest(
    requestType = "update",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = AccountDebug(
        mode = AccountRequestDebugMode.STUB,
        stub = AccountRequestDebugStubs.BAD_OWNER_ID,
    ),
    account = AccountUpdateObject(
        description = "stub description",
        ownerId = fromString("cd565097-4b69-490e-b167-b59128475562"),
        currency = "RUB",
        id = "26c45c31-857f-4d5d-bf59-890817c9320b",
        lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
        balance = 1005_00,
        status = AccountStatus.FROZEN,
    ),
)


val someSearchAccount = AccountSearchRequest(
    requestType = "search",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = AccountDebug(
        mode = AccountRequestDebugMode.STUB,
        stub = AccountRequestDebugStubs.BAD_OWNER_ID,
    ),
    accountFilter = AccountSearchFilter(
        searchString = "stub search string",
        ownerId = fromString("cd565097-4b69-490e-b167-b59128475562"),
    ),
)


val someHistoryAccount = AccountHistoryRequest(
    requestType = "history",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = AccountDebug(
        mode = AccountRequestDebugMode.STUB,
        stub = AccountRequestDebugStubs.BAD_OWNER_ID,
    ),
    account = AccountHistoryObject(id = "26c45c31-857f-4d5d-bf59-890817c9320b"),
)


val someTransactAccount = AccountTransactRequest(
    requestType = "transact",
    requestId = "75038a32-9d63-4394-968b-d33aaedc057e",
    debug = AccountDebug(
        mode = AccountRequestDebugMode.STUB,
        stub = AccountRequestDebugStubs.BAD_OWNER_ID,
    ),
    account = AccountTransactObject(
        id = "26c45c31-857f-4d5d-bf59-890817c9320b",
        lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
    ),
    transaction = AccountTransaction(
        type = TransactionType.WITHDRAW,
        amount = 1005_00,
        counterparty = "11102220333044405550",
        description = "stub transaction description",
        timestamp = "2023-07-04T18:43:00.123456789Z",
    ),
)
