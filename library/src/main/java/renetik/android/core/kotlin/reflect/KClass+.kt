package renetik.android.core.kotlin.reflect

import renetik.android.core.lang.catchAllWarnReturnNull
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

fun <T : Any> KClass<T>.createInstance() = catchAllWarnReturnNull {
    java.getDeclaredConstructor().run {
        isAccessible = true
        newInstance()
    }
}

inline fun <reified T : Any> createInstance(): T = T::class.createInstance()