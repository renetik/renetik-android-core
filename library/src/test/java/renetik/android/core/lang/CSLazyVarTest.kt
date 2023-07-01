package renetik.android.core.lang

import org.junit.Assert.assertEquals
import org.junit.Test
import renetik.android.core.lang.lazy.CSLazyVar.Companion.lazyVar

class CSLazyVarTest {
    @Test
    fun testLazyVar() {
        var testVar: String by lazyVar { "initial" }
        assertEquals("initial", testVar)
        testVar = "test"
        assertEquals("test", testVar)
    }
}