package com.orka.myfinances.lib.data

abstract class AbstractRepository<T, R>(
    private val dataSource: DataSource<R>,
    private val mapper: Mapper<T, R>
) : Repository<T> {

    override suspend fun get(): List<T>? {
        val r = dataSource.get()
        return r?.map { mapper.map(it) }
    }
}