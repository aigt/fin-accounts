import aigt.finaccounts.common.models.error.ContextError
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.logging.v1.mapper.fixture.getCreateFinAccountsContext
import aigt.finaccounts.logging.v1.mapper.toLog
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun fromContext() {
        val context = getCreateFinAccountsContext().apply {
            errors.add(
                ContextError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                    exception = null,
                ),
            )
            state = ContextState.RUNNING
        }

        val log = context.toLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("finaccounts", log.source)
        assertEquals(
            "75038a32-9d63-4394-968b-d33aaedc057e",
            log.account?.requestId,
        )
        assertEquals("10002000300040005000", log.account?.accountResponse?.id)

        val error = log.errors?.firstOrNull()
        assertEquals("wrong title", error?.message)
        assertEquals("request", error?.group)
    }
}
