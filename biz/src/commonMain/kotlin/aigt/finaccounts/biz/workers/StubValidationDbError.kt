package aigt.finaccounts.biz.workers

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

fun ICorChainDsl<FinAccountsContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == ContextStubCase.DB_ERROR && state == ContextState.RUNNING }
    handle {
        state = ContextState.FAILING
        this.errors.add(
            ContextError(
                group = "internal",
                code = "internal-db",
                message = "Internal error",
            ),
        )
    }
}
