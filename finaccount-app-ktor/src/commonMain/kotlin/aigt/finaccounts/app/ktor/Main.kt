package aigt.finaccounts.app.ktor

import aigt.finaccounts.app.ktor.common.module
import io.ktor.server.cio.*
import io.ktor.server.engine.*


expect fun main(args: Array<String>)

fun mainKMP(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    embeddedServer(CIO, port = 8080) { module() }.start(wait = true)
}
