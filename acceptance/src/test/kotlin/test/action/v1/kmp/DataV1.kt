package aigt.finaccounts.blackbox.test.action.v1.kmp

import aigt.finaccounts.api.v1.kmp.models.AccountCreateObject
import aigt.finaccounts.api.v1.kmp.models.AccountDebug
import aigt.finaccounts.api.v1.kmp.models.AccountHistoryObject
import aigt.finaccounts.api.v1.kmp.models.AccountPermissions
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugMode
import aigt.finaccounts.api.v1.kmp.models.AccountRequestDebugStubs
import aigt.finaccounts.api.v1.kmp.models.AccountResponseObject
import aigt.finaccounts.api.v1.kmp.models.AccountSearchFilter
import aigt.finaccounts.api.v1.kmp.models.AccountStatus
import aigt.finaccounts.api.v1.kmp.models.AccountTransactObject
import aigt.finaccounts.api.v1.kmp.models.AccountTransaction
import aigt.finaccounts.api.v1.kmp.models.TransactionType
import aigt.finaccounts.stubs.SimpleAccountsStub


val debug = AccountDebug(
    mode = AccountRequestDebugMode.STUB,
    stub = AccountRequestDebugStubs.SUCCESS,
)


val accountStub = AccountResponseObject(
    description = SimpleAccountsStub.ACCOUNT_DESCRIPTION.asString(),
    ownerId = SimpleAccountsStub.ACCOUNT_OWNER_ID.asString(),
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
    description = SimpleAccountsStub.ACCOUNT_DESCRIPTION.asString(),
    ownerId = SimpleAccountsStub.ACCOUNT_OWNER_ID.asString(),
    currency = "RUB",
)


val someSearchFilter = AccountSearchFilter(
    searchString = "stub search string",
    ownerId = "cd565097-4b69-490e-b167-b59128475562",
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
