package renetik.android.core.lang

typealias void = Unit
typealias Func = () -> void
typealias ArgFunc<Arg> = (Arg) -> void
typealias ArgArgFunc<Arg1, Arg2> = (Arg1, Arg2) -> void
typealias ReturnFunc<Return> = () -> Return
typealias ArgReturnFunc<Arg, Return> = (Arg) -> Return