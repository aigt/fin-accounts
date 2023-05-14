import kotlin.test.Test
import kotlin.test.assertEquals

class MainKtTest {

    @Test
    fun `simple multiplication`() {
        assertEquals(4, stubMultiply(2, 2))
    }
}