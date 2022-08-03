package renetik.android.core.kotlin.primitives

import org.junit.Assert
import org.junit.Test

class FloatTest {
    @Test
    fun restTest() {
        Assert.assertEquals(0.99f, 0.99f.rest)
        Assert.assertEquals(0.99f, 8.99f.rest)
        Assert.assertEquals(0f, 0f.rest)
        Assert.assertEquals(0f, 1f.rest)
        Assert.assertEquals(0.02f, 1.2f.rest)
    }

}
