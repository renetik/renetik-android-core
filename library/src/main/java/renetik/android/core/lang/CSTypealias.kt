package renetik.android.core.lang

typealias Func = () -> Unit
typealias ArgFunc<Arg> = (Arg) -> Unit
typealias ArgArgFunc<Arg1, Arg2> = (Arg1, Arg2) -> Unit
typealias ReturnFunc<Return> = () -> Return
typealias ArgReturnFunc<Arg, Return> = (Arg) -> Return

operator fun <T> ArgFunc<T>?.invoke(type: T) {
    this?.invoke(type)
}