package aigt.finaccounts.biz.workers

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

fun ICorChainDsl<FinAccountsContext>.initStatus(title: String) = worker {
    this.title = title
    on { state == ContextState.NONE }
    handle { state = ContextState.RUNNING }
}
