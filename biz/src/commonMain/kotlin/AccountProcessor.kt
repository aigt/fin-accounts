package aigt.finaccounts.biz

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.account.AccountBalance
import aigt.finaccounts.common.models.account.AccountCurrency
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.request.RequestStartTime
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.common.models.transaction.TransactionType
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.stubs.AccountStub
import com.benasher44.uuid.uuid4
import kotlinx.datetime.toInstant

class AccountProcessor {
    suspend fun exec(ctx: FinAccountsContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == ContextWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        ctx.requestStartTime =
            RequestStartTime("2023-08-04T18:43:00.123456789Z".toInstant())
        ctx.state = ContextState.RUNNING

        when (ctx.command) {
            ContextCommand.HISTORY -> {
                val accountId = uuid4().toString()
                ctx.accountResponse = AccountStub.get()
                ctx.historyResponse
                    .addAll(
                        AccountStub.prepareTransactionsList(
                            listSize = 6,
                            filter = "на чай",
                            accountId = TransactionAccountId(accountId),
                            type = TransactionType.INCOME,
                        ),
                    )
            }

            ContextCommand.TRANSACT -> {
                ctx.accountResponse = AccountStub.transact()
                ctx.historyResponse.add(ctx.transactionRequest)
            }

            ContextCommand.SEARCH -> {
                ctx.accountsResponse.addAll(
                    AccountStub.prepareAccountsList(
                        listSize = 10,
                        currency = AccountCurrency(code = "XYZ"),
                        balance = AccountBalance(cents = 100),
                        filter = ctx.accountFilter,
                    ),
                )
            }

            else -> {
                ctx.accountResponse = AccountStub.get()
            }
        }
    }
}
