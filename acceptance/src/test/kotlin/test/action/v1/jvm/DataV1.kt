package aigt.finaccounts.blackbox.test.action.v1.jvm

import aigt.finaccounts.api.v1.jackson.models.AccountCreateObject
import aigt.finaccounts.api.v1.jackson.models.AccountDebug
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryObject
import aigt.finaccounts.api.v1.jackson.models.AccountPermissions
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.jackson.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountSearchFilter
import aigt.finaccounts.api.v1.jackson.models.AccountStatus
import aigt.finaccounts.api.v1.jackson.models.AccountTransactObject
import aigt.finaccounts.api.v1.jackson.models.AccountTransaction
import aigt.finaccounts.api.v1.jackson.models.TransactionType
import java.util.UUID.fromString


val debug = AccountDebug(
    mode = AccountRequestDebugMode.STUB,
    stub = AccountRequestDebugStubs.SUCCESS,
)


val accountStub = AccountResponseObject(
    description = "Простой аккаунт",
    ownerId = fromString("9deb6b8c-b797-4b34-9201-776ae1d3cf58"),
    currency = "RUB",
    id = "10002000300040005000",
    lock = null,
    lastTransaction = "2023-06-26T18:43:00.123456789Z",
    permissions = setOf(
        AccountPermissions.READ,
    ),
    balance = 1200_00,
    status = AccountStatus.ACTIVE,
)


val someCreateAccount = AccountCreateObject(
    description = "stub description",
    ownerId = fromString("cd565097-4b69-490e-b167-b59128475562"),
    currency = "RUB",
)


val someSearchFilter = AccountSearchFilter(
    searchString = "stub search string",
    ownerId = fromString("cd565097-4b69-490e-b167-b59128475562"),
)


val someHistoryAccount =
    AccountHistoryObject(id = "26c45c31-857f-4d5d-bf59-890817c9320b")


val someTransactAccount = AccountTransactObject(
    id = "26c45c31-857f-4d5d-bf59-890817c9320b",
    lock = "a3c0cffb-97d3-4e9d-898d-10eb10470501",
)


val someTransaction = AccountTransaction(
    type = TransactionType.WITHDRAW,
    amount = 1005_00,
    counterparty = "11102220333044405550",
    description = "stub transaction description",
    timestamp = "2023-07-04T18:43:00.123456789Z",
)
