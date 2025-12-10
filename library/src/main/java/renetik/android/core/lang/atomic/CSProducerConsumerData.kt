package renetik.android.core.lang.atomic

import java.util.concurrent.atomic.AtomicReference

class CSProducerConsumerData<T : Any>(private val initial: () -> T) {
    private val consumerData = AtomicReference(initial())
    private var producerData: T = initial()
    val active: T get() = consumerData.get()
    val prepared: T get() = producerData

    fun swap() {
        val oldActive = consumerData.getAndSet(producerData)
        producerData = oldActive
    }

    fun clear() {
        consumerData.set(initial())
        producerData = initial()
    }

    fun prepare(value: T) {
        producerData = value
    }
}