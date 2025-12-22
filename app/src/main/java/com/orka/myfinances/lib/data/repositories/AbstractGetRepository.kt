package com.orka.myfinances.lib.data.repositories

import com.orka.myfinances.lib.data.Mapper
import com.orka.myfinances.lib.data.source.DataSource

abstract class AbstractGetRepository<T, R>(
    private val dataSource: DataSource<R>,
    private val mapper: Mapper<T, R>
) : GetRepository<T> {

    override suspend fun get(): List<T>? {
        val r = dataSource.get()
        return r?.map { mapper.map(it) }
    }
}