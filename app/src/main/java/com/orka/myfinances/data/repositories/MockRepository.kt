package com.orka.myfinances.data.repositories

import com.orka.myfinances.lib.data.Repository
import kotlinx.coroutines.delay

abstract class GetRepository<T>(
    items: List<T>,
    private val duration: Long = 1000
) : Repository<T> {
    protected val items = items.toMutableList()

    override suspend fun get(): List<T>? {
        delay(duration)
        return items.toList()
    }
}

abstract class MockRepository<T, R>(
    items: List<T>,
    private val duration: Long = 1000
) : GetRepository<T>(items, duration) {

    suspend fun add(request: R): T? {
        delay(duration)
        return if(acceptable(request)) {
            val r = map(request)
            items.add(r)
            r
        } else null
    }

    protected open fun acceptable(request: R): Boolean {
        return true
    }

    protected abstract fun map(request: R): T
}