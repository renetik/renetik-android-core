package renetik.android.core.kotlin

import renetik.android.core.java.lang.createInstance
import renetik.android.core.logging.CSLog.logWarn
import kotlin.reflect.KClass

fun <T> Any.privateField(name: String): T? = runCatching {
    val field = this::class.java.getDeclaredField(name)
    field.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    return field.get(this) as T
}.onFailure(::logWarn).getOrNull()

fun <T> Any.setPrivateField(name: String, fieldValue: T) = runCatching {
    val field = this::class.java.getDeclaredField(name)
    field.isAccessible = true
    field.set(this, fieldValue)
}.onFailure(::logWarn)

@Suppress("UNCHECKED_CAST")
fun <T> createClass(className: String): Class<T>? = runCatching {
    Class.forName(className) as? Class<T>
}.onFailure(::logWarn).getOrNull()

inline fun <reified T> createInstance(): T? =
    T::class.java.createInstance()

fun classExist(name: String): Boolean = runCatching {
    Class.forName(name); true
}.getOrDefault(false)

fun <T> createInstance(className: String): T? = createClass<T>(className)?.createInstance()

@Suppress("UNCHECKED_CAST")
fun <T> createInstance(className: String, vararg arguments: Any): T? =
    createClass<T>(className)?.constructors?.firstOrNull {
        it.parameterTypes.size == arguments.size
    }?.newInstance(*arguments) as? T

fun <T> Class<T>?.invoke(function: String, argument: T? = null): Any? =
    this?.getMethod(function)?.invoke(argument)

fun Any.invokeFunction(name: String, argument: Any? = null): Any? = runCatching {
    javaClass.getMethod(name).also { it.isAccessible = true }.invoke(this, argument)
}.getOrNull()

fun <T> Any.invokeFunction(name: String, vararg argument: Any): Any? = runCatching {
    javaClass.getMethod(name).also { it.isAccessible = true }.invoke(this, argument)
}.getOrNull()

val <T : Any> T.kClass: KClass<T> get() = javaClass.kotlin