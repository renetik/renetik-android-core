<!---Header--->
[![Android Build](https://github.com/renetik/renetik-android-core/workflows/Android%20Build/badge.svg)
](https://github.com/renetik/renetik-android-core/actions/workflows/android.yml)

# Renetik Android - Core
#### [https://github.com/renetik/renetik-android-core](https://github.com/renetik/renetik-android-core/) ➜ [Documentation](https://renetik.github.io/renetik-android-core/)

Framework to enjoy, improve and speed up your application development while writing readable code.
Used as library in many projects and improving it while developing new projects.
I am open for [Hire](https://renetik.github.io) or investment in my mobile app music production & perfromance project Renetik Instruments www.renetik.com.

```gradle
allprojects {
    repositories {
        // For master-SNAPSHOT
        maven { url 'https://github.com/renetik/maven-snapshot/raw/master/repository' }
        // For release builds
        maven { url 'https://github.com/renetik/maven/raw/master/repository' }
    }
}
```
```gradle
dependencies {
    implementation 'com.renetik.library:renetik-android-core:$renetik-android-version'
}
```
## Examples
```kotlin
    @Test
    fun testLazyVar() {
        var testVar: String by lazyVar { "initial" }
        assertEquals("initial", testVar)
        testVar = "test"
        assertEquals("test", testVar)
    }

    @Test
    fun testNullableLazyVar() {
        var testVar: String? by lazyVar { "initial" }
        assertEquals("initial", testVar)
        testVar = "test"
        assertEquals("test", testVar)
        testVar = null
        assertEquals(null, testVar)
    }

```
```kotlin
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
```
```kotlin
class CSAndroidLoggerTest {
    var event: CSLoggerEvent? = null
    var message: String? = null
    private val listener = { event: CSLoggerEvent, message: String ->
        this.event = event
        this.message = message
    }

    @Test
    fun logWithListener() {
        init(CSAndroidLogger(name = "TestLog", isDebug = true, listener))
        logWarn("test")
        Assert.assertEquals(Warn, event)
        Assert.assertTrue(message!!.endsWith("test"))
    }

    @Test
    fun isDebug() {
        init(CSAndroidLogger(name = "TestLog", isDebug = false, listener))
        logDebug { "test" }
        Assert.assertNull(event)

        init(CSAndroidLogger(name = "TestLog", isDebug = true, listener))
        logDebug { "test2" }
        Assert.assertEquals(Debug, event)
        Assert.assertTrue(message!!.endsWith("test2"))
    }
}
```

## Renetik Android - Libraries
#### [https://github.com/renetik/renetik-android-core](https://github.com/renetik/renetik-android-core/) ➜ [Documentation](https://renetik.github.io/renetik-android-core/)
#### [https://github.com/renetik/renetik-android-json](https://github.com/renetik/renetik-android-json/) ➜ [Documentation](https://renetik.github.io/renetik-android-json/)
#### [https://github.com/renetik/renetik-android-event](https://github.com/renetik/renetik-android-event/) ➜ [Documentation](https://renetik.github.io/renetik-android-event/)
#### [https://github.com/renetik/renetik-android-store](https://github.com/renetik/renetik-android-store/) ➜ [Documentation](https://renetik.github.io/renetik-android-store/)
#### [https://github.com/renetik/renetik-android-preset](https://github.com/renetik/renetik-android-preset/) ➜ [Documentation](https://renetik.github.io/renetik-android-preset/)
#### [https://github.com/renetik/renetik-android-framework](https://github.com/renetik/renetik-android-framework/) ➜ [Documentation](https://renetik.github.io/renetik-android-framework/)
