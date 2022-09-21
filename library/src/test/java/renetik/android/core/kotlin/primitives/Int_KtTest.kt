package renetik.android.core.kotlin.primitives

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class IntTest {

    @Test
    fun unique() {
        val value1 = Int.unique(9)
        val value2 = Int.unique(9)
        val value3 = Int.unique(9)
        val value4 = Int.unique(9)
        assertNotEquals(value1, value2)
        assertNotEquals(value2, value3)
        assertNotEquals(value3, value4)
        assertEquals(9, value1.toString().length)
        assertEquals(9, value2.toString().length)
        assertEquals(9, value3.toString().length)
        assertEquals(9, value4.toString().length)
    }
}