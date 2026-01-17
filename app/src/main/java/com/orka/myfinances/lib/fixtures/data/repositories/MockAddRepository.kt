package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.lib.data.repositories.AddRepository

abstract class MockAddRepository<T, R> : AddRepository<T, R> {
    override suspend fun add(request: R): T? {
        return if(acceptable(request)) {
            request.map()
        } else null
    }

    protected abstract fun R.map(): T
    protected open fun acceptable(request: R): Boolean = true
}