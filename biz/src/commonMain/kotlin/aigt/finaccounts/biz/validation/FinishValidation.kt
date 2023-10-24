package aigt.finaccounts.biz.validation

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

fun ICorChainDsl<FinAccountsContext>.finishAccountValidation(title: String) =
    worker {
        this.title = title
        on { state == ContextState.RUNNING }
        handle {
            accountValidated = accountValidating
        }
    }

fun ICorChainDsl<FinAccountsContext>.finishAccountFilterValidation(title: String) =
    worker {
        this.title = title
        on { state == ContextState.RUNNING }
        handle {
            accountFilterValidated = accountFilterValidating
        }
    }

fun ICorChainDsl<FinAccountsContext>.finishTransactionValidation(title: String) =
    worker {
        this.title = title
        on { state == ContextState.RUNNING }
        handle {
            transactionValidating = transactionValidated
        }
    }

