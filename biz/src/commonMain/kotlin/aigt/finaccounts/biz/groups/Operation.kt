package aigt.finaccounts.biz.groups

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.chain

fun ICorChainDsl<FinAccountsContext>.operation(
    title: String,
    command: ContextCommand,
    block: ICorChainDsl<FinAccountsContext>.() -> Unit,
) = chain {
    block()
    this.title = title
    on { this.command == command && state == ContextState.RUNNING }
}
