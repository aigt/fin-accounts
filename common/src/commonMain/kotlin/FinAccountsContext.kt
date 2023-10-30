package aigt.finaccounts.common

import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.request.RequestStartTime
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.Transaction
import aigt.finaccounts.common.models.workmode.ContextWorkMode

data class FinAccountsContext(
    var command: ContextCommand = ContextCommand.NONE,
    var state: ContextState = ContextState.NONE,
    val errors: MutableList<ContextError> = mutableListOf(),

    var workMode: ContextWorkMode = ContextWorkMode.PROD,
    var stubCase: ContextStubCase = ContextStubCase.NONE,

    var requestId: RequestId = RequestId.NONE,
    var requestStartTime: RequestStartTime = RequestStartTime.NONE,

    var accountFilter: AccountFilter = AccountFilter.NONE,

    var accountRequest: Account = Account.NONE,
    var transactionRequest: Transaction = Transaction.NONE,

    var accountValidating: Account = Account.NONE,
    var transactionValidating: Transaction = Transaction.NONE,
    var accountFilterValidating: AccountFilter = AccountFilter.NONE,

    var accountValidated: Account = Account.NONE,
    var transactionValidated: Transaction = Transaction.NONE,
    var accountFilterValidated: AccountFilter = AccountFilter.NONE,

    var accountResponse: Account = Account.NONE,
    var accountsResponse: MutableList<Account> = mutableListOf(),
    var historyResponse: MutableList<Transaction> = mutableListOf(),
)
