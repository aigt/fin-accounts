package aigt.finaccounts.biz.groups

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.chain

fun ICorChainDsl<FinAccountsContext>.stubs(
    title: String,
    block: ICorChainDsl<FinAccountsContext>.() -> Unit,
) = chain {
    block()
    this.title = title
    on { workMode == ContextWorkMode.STUB && state == ContextState.RUNNING }
}
