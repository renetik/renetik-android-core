package renetik.android.core.kotlin

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AnyCSNullTest {
    @Test
    fun notNullTest() {
        var a: String? = ""
        var b: String? = ""
        var c: String? = ""
        assertTrue(isAllNotNull(a, b, c))
        a = null
        assertTrue(isAnyNotNull(a, b, c))
        b = null; c = null
        assertTrue(isAllNull(a, b, c))
    }

    @Test
    fun isNullTest() {
        var a: String? = ""
        var b: String? = ""
        var c: String? = ""
        assertFalse(isAnyNull(a, b, c))
        a = null
        assertTrue(isAnyNull(a, b, c))
        b = null; c = null
        assertTrue(isAllNull(a, b, c))
    }
}