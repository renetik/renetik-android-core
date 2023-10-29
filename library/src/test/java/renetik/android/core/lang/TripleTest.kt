package renetik.android.core.lang

import org.junit.Assert.assertEquals
import org.junit.Test

class TripleTest {

    @Test
    fun to() {
        val something = 45 to "Apple" to 57f
        val (number: Int, string: String, float: Float) = something
        assertEquals(45, number)
        assertEquals("Apple", string)
        assertEquals(57f, float)
    }
}