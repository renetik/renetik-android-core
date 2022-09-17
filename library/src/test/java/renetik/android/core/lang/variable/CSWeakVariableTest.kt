package renetik.android.core.lang.variable

import org.junit.Assert.*
import org.junit.Test
import renetik.android.core.lang.variable.CSWeakVariable.Companion.weak

class CSWeakVariableTest {

    class TestObject

    var testVar: TestObject? by weak()

    @Test
    fun testVar() {
        assertNull(testVar)
        var instance: TestObject? = TestObject()
        testVar = instance
        assertEquals(instance, testVar)
        instance = null
        System.gc()
        assertNull(testVar)
    }

    @Test
    fun testVarNulling() {
        val instance = TestObject()
        var testVar: TestObject? by weak(instance)
        System.gc()
        assertNotNull(testVar)
        testVar = null
        System.gc()
        assertNull(testVar)
    }

    @Test
    fun testVarNull() {
        val testVar: TestObject? by weak(TestObject())
        System.gc()
        assertNull(testVar)
    }

    @Test
    fun testVarMultiple() {
        assertNull(testVar)
        var instance1: TestObject? = TestObject()
        var instance2: TestObject? = TestObject()
        testVar = instance1
        System.gc()
        assertEquals(instance1, testVar)
        instance1 = null
        System.gc()
        assertNull(testVar)

        testVar = instance2
        System.gc()
        assertEquals(instance2, testVar)
        instance2 = null
        System.gc()
        assertNull(testVar)
    }
}