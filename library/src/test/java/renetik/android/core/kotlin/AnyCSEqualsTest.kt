package renetik.android.core.kotlin

import org.junit.Assert
import org.junit.Test

class AnyCSEqualsTest {

    @Test
    fun equalsAny() {
        Assert.assertTrue("third".equalsAny("first", "second", "third"))
        Assert.assertFalse("fourth".equalsAny("first", "second", "third"))

        val values = listOf("first", "second", "third")
        Assert.assertTrue("third" equalsAny values)
        Assert.assertFalse("fourth" equalsAny values)
    }

    @Test
    fun equalsNone() {
        Assert.assertTrue("fourth".equalsNone("first", "second", "third"))
        Assert.assertFalse("second".equalsNone("first", "second", "third"))

        val values = listOf("first", "second", "third")
        Assert.assertTrue("fourth" equalsNone values)
        Assert.assertFalse("first" equalsNone values)
    }

    @Test
    fun equalsAll() {
        Assert.assertTrue("fourth".equalsAll("fourth", "fourth", "fourth"))
        Assert.assertFalse("fourth".equalsAll("first", "second", "third"))

        Assert.assertTrue("fourth" equalsAll listOf("fourth", "fourth", "fourth"))
        Assert.assertFalse("fourth" equalsAll listOf("first", "second", "third"))
    }
}