package aigt.finaccounts.common

import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.accountfilter.AccountFilter
import aigt.finaccounts.common.models.account.Account
import aigt.finaccounts.common.models.request.RequestId
import aigt.finaccounts.common.models.request.RequestStartTime
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.common.models.transaction.Transaction

data class FinAccountsContext(
    var command: ContextCommand = ContextCommand.NONE,
    var state: ContextState = ContextState.NONE,
    val errors: MutableList<ContextError> = mutableListOf(),

    var workMode: ContextWorkMode = ContextWorkMode.PROD,
    var stubCase: ContextStubCase = ContextStubCase.NONE,

    var requestId: RequestId = RequestId.NONE,
    var requestStartTime: RequestStartTime = RequestStartTime.NONE,

    var accountFilter: AccountFilter = AccountFilter(),

    var accountRequest: Account = Account(),

    var accountResponse: Account = Account(),
    var accountsResponse: MutableList<Account> = mutableListOf(),
    var historyResponse: MutableList<Transaction> = mutableListOf(),
)
