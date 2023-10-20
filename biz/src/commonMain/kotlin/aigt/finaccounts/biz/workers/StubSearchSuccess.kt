package aigt.finaccounts.biz.workers

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker
import aigt.finaccounts.stubs.AccountStub

fun ICorChainDsl<FinAccountsContext>.stubSearchSuccess(title: String) =
    worker {
        this.title = title
        on { stubCase == ContextStubCase.SUCCESS && state == ContextState.RUNNING }
        handle {
            state = ContextState.FINISHING

            accountsResponse.addAll(
                AccountStub.prepareAccountsList(
                    listSize = 10,
                    currency = AccountCurrency(code = "XYZ"),
                    balance = AccountBalance(cents = 100),
                    filter = accountFilter,
                ),
            )
        }
    }
