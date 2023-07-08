package aigt.finaccounts.mappers.kmp.v1.exceptions

import aigt.finaccounts.common.models.command.ContextCommand

class MappingUnknownCommand(command: ContextCommand) :
    Throwable("Wrong command $command at mapping toTransport stage")
