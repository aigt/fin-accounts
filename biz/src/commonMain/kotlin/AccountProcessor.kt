package aigt.finaccounts.biz

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.common.models.transaction.TransactionType
import aigt.finaccounts.common.models.workmode.ContextWorkMode
import aigt.finaccounts.stubs.AccountStub
import com.benasher44.uuid.uuid4

class AccountProcessor {
    suspend fun exec(ctx: FinAccountsContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == ContextWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            ContextCommand.HISTORY -> {
                val accountId = uuid4().toString()
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
            }

            else -> {
                ctx.accountResponse = AccountStub.get()
            }
        }
    }
}
