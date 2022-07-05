package renetik.android.core.kotlin

import org.junit.Assert.*
import org.junit.Test
import renetik.android.core.lang.property.CSProperty

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

    @Test
    fun propertyIsNullTest() {
        val property = object : CSProperty<String?> {
            override var value: String? = null
        }
        assertTrue(property.isNull)
        property.value = ""
        assertTrue(property.notNull)
        property.value = null
        assertNotNull(property.isNull)
    }
}