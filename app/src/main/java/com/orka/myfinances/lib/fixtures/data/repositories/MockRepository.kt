package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.lib.data.repositories.AddRepository
import kotlinx.coroutines.delay

abstract class MockRepository<T, R>(
    items: List<T>,
    duration: Long = 1000
) : MockGetRepository<T>(items, duration), AddRepository<T, R> {

    override suspend fun add(request: R): T? {
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