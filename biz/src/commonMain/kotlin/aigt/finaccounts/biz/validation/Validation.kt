package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.chain

fun ICorChainDsl<FinAccountsContext>.validation(block: ICorChainDsl<FinAccountsContext>.() -> Unit) =
    chain {
        block()
        title = "Валидация"

        on { state == ContextState.RUNNING }
    }
