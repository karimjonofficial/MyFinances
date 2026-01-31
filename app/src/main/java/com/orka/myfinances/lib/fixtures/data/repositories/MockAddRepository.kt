package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.lib.data.repositories.Add
import kotlinx.coroutines.delay

interface MockAddRepository<T, R> : Add<T, R>, Repository {
    val items: MutableList<T>

    override suspend fun add(request: R): T? {
        delay(duration)

        if(acceptable(request)) {
            val item = request.map()
            items.add(item)
            return item
        } else return null
    }

    suspend fun R.map(): T
    suspend fun acceptable(request: R): Boolean = true
}