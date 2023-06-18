package aigt.finaccounts.mappers.v1.exceptions

import aigt.finaccounts.common.models.command.ContextCommand

class UnknownContextCommand(command: ContextCommand) : Throwable("Wrong command $command at mapping toTransport stage")
