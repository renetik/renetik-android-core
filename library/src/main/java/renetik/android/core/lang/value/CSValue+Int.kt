package renetik.android.core.lang.value

operator fun CSValue<Int>.plus(value: Int): Int = this.value + value

operator fun CSValue<Int>.compareTo(value: Int): Int = this.value.compareTo(value)