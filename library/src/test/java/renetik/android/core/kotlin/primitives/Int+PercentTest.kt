package renetik.android.core.kotlin.primitives

import org.junit.Test
import renetik.android.testing.CSAssert.assert

class IntPlusPercentTest {

    @Test
    fun toPercentOfRange() {
        val range = 10..22000
        val valuePercent = 10.toPercentOf(range)
        assert(expected = 0f, valuePercent)
        assert(expected = 0f, valuePercent.percentOf(127f))
    }
}