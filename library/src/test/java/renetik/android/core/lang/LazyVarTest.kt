package renetik.android.core.lang

import org.junit.Assert.assertEquals
import org.junit.Test
import renetik.android.core.lang.lazy.nullableLazyVar

class CSNullableLazyVarTest {
    @Test
    fun testLazyVar() {
        var testVar: String by nullableLazyVar { "initial" }
        assertEquals("initial", testVar)
        testVar = "test"
        assertEquals("test", testVar)
    }

    @Test
    fun testNullableLazyVar() {
        var testVar: String? by nullableLazyVar { "initial" }
        assertEquals("initial", testVar)
        testVar = "test"
        assertEquals("test", testVar)
        testVar = null
        assertEquals(null, testVar)
    }
}