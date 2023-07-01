package renetik.android.core.lang

import org.junit.Assert
import org.junit.Test
import renetik.android.core.lang.lazy.CSLazyNullableVar.Companion.lazyNullableVar

class CSNullableLazyVarTest {
    @Test
    fun testLazyNullableVar() {
        var testVar: String? by lazyNullableVar { "initial" }
        Assert.assertEquals("initial", testVar)
        testVar = "test"
        Assert.assertEquals("test", testVar)
        testVar = null
        Assert.assertEquals(null, testVar)
    }
}