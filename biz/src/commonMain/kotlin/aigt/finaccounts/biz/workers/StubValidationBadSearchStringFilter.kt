package aigt.finaccounts.biz.workers

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker

fun ICorChainDsl<FinAccountsContext>.stubValidationBadSearchStringFilter(title: String) =
    worker {
        this.title = title
        on { stubCase == ContextStubCase.BAD_SEARCH_STRING_FILTER && state == ContextState.RUNNING }
        handle {
            state = ContextState.FAILING
            this.errors.add(
                ContextError(
                    group = "validation",
                    code = "validation-search-string-filter",
                    field = "search-string-filter",
                    message = "Wrong search string filter field",
                ),
            )
        }
    }
