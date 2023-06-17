package aigt.finaccounts.common.helpers

import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.models.command.ContextCommand

fun FinAccountsContext.isUpdatableCommand() =
    this.command in listOf(
        ContextCommand.CREATE,
        ContextCommand.UPDATE,
        ContextCommand.TRANSACT,
    )
