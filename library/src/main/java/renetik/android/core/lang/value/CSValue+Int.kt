package renetik.android.core.lang.value

operator fun Int.plus(value: CSValue<Int>): Int = this + value.value
operator fun CSValue<Int>.plus(value: CSValue<Int>): Int = value.value + value.value
operator fun CSValue<Int>.plus(value: Int): Int = this.value + value
operator fun CSValue<Int>.compareTo(value: Int): Int = this.value.compareTo(value)
operator fun CSValue<Int>.compareTo(value: CSValue<Int>): Int = this.value.compareTo(value.value)