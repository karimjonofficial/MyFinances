package com.orka.myfinances.lib.data

abstract class AbstractDataSource<T, R>(
    private val apiService: ApiService<R>,
    private val storage: Storage<R>,
    private val mapper: Mapper<T, R>
) : DataSource<T> {

    override suspend fun get(): List<T>? {
        val sList = storage.get()
        val aList = apiService.get()
        return if(sList != null) {
            if(aList != null) {
                (sList + aList).distinctBy { it }.map { mapper.map(it) }
            } else sList.map { mapper.map(it) }
        } else aList?.map { mapper.map(it) }
    }
}