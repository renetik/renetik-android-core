package renetik.android.core.kotlin

import renetik.android.core.java.lang.createInstance
import renetik.android.core.lang.catchAllWarn
import renetik.android.core.lang.catchAllWarnReturnNull
import kotlin.reflect.KClass

const val INVOKE_FAILED = "invoke_failed"

fun <T> Any.privateField(name: String): T? = catchAllWarnReturnNull<T> {
    val field = this::class.java.getDeclaredField(name)
    field.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    return field.get(this) as T
}

fun <T> Any.setPrivateField(name: String, fieldValue: T) = catchAllWarn {
    val field = this::class.java.getDeclaredField(name)
    field.isAccessible = true
    field.set(this, fieldValue)
}

inline fun <reified ClassType : Any>
        ClassType.setPrivateField2(name: String, fieldValue: Any) = catchAllWarn {
    val field = ClassType::class.java.getDeclaredField(name)
    field.isAccessible = true
    field.set(this, fieldValue)
}

@Suppress("UNCHECKED_CAST")
fun <T> createClass(className: String) =
    catchAllWarnReturnNull { Class.forName(className) } as? Class<T>

inline fun <reified T> createInstance(): T? =
    T::class.java.createInstance()

fun classExist(name: String): Boolean = try {
    Class.forName(name)
    true
} catch (ignored: ClassNotFoundException) {
    false
}

fun <T> createInstance(className: String): T? =
    createClass<T>(className)?.createInstance()

@Suppress("UNCHECKED_CAST")
fun <T> createInstance(className: String, vararg arguments: Any): T? =
    createClass<T>(className)?.constructors?.firstOrNull {
        it.parameterTypes.size == arguments.size
    }?.newInstance(*arguments) as? T

fun <T> Class<T>?.invoke(function: String, argument: T? = null): Any? =
    this?.getMethod(function)?.invoke(argument)

fun invokeFunction(type: Class<*>, name: String,
                   argumentTypes: Array<Class<*>>, arguments: Array<Any>
): Any? = try {
    type.getDeclaredMethod(name, *argumentTypes)
        .apply { isAccessible = true }.invoke(null, *arguments)
} catch (e: Exception) {
    INVOKE_FAILED
}

val <T : Any> T.kClass: KClass<T> get() = javaClass.kotlin