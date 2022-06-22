package renetik.android.core

import org.junit.Assert.assertEquals
import org.junit.Test
import renetik.android.core.kotlin.primitives.leaveEndOfLength

class StringTest {
    @Test
    fun leaveEndOfLength3Last() = assertEquals("678", "12345678".leaveEndOfLength(3))

    @Test
    fun leaveEndOfLength15Last() = assertEquals("12345678", "12345678".leaveEndOfLength(15))
}
