package aigt.finaccounts.biz.workers

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.account.AccountDescription
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.account.AccountLastTransactionTime
import aigt.finaccounts.common.models.account.AccountOwnerId
import aigt.finaccounts.common.models.account.AccountStatus
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.stubcase.ContextStubCase
import aigt.finaccounts.cor.ICorChainDsl
import aigt.finaccounts.cor.worker
import aigt.finaccounts.stubs.AccountStub

fun ICorChainDsl<FinAccountsContext>.stubOffersSuccess(title: String) =
    worker {
        this.title = title
        on { stubCase == ContextStubCase.SUCCESS && state == ContextState.RUNNING }
        handle {
            state = ContextState.FINISHING

            val stub = AccountStub.transact().apply {
                accountRequest.description.takeIf { it != AccountDescription.NONE }
                    ?.also { this.description = it }
                accountRequest.ownerId.takeIf { it != AccountOwnerId.NONE }
                    ?.also { this.ownerId = it }
                accountRequest.balance.takeIf { it != AccountBalance.NONE }
                    ?.also { this.balance = it }
                accountRequest.currency.takeIf { it != AccountCurrency.NONE }
                    ?.also { this.currency = it }
                accountRequest.status.takeIf { it != AccountStatus.NONE }
                    ?.also { this.status = it }
                accountRequest.id.takeIf { it != AccountId.NONE }
                    ?.also { this.id = it }
                accountRequest.lastTransactionTime.takeIf { it != AccountLastTransactionTime.NONE }
                    ?.also { this.lastTransactionTime = it }
                accountRequest.permissionsClient.takeIf { it.isNotEmpty() }
                    ?.also {
                        this.permissionsClient.clear()
                        this.permissionsClient.addAll(it)
                    }
            }
            accountResponse = stub

            historyResponse.add(transactionRequest)
        }
    }
