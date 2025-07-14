package com.orka.myfinances.fixtures

import com.orka.myfinances.datasources.CategoryDataSource
import com.orka.myfinances.models.Category
import kotlinx.coroutines.delay

class CategoryDataSourceImpl : CategoryDataSource {
    private var index = 0
    private var list: List<Category>? = null

    override suspend fun get(): List<Category>? {
        val list = this.list ?: (1..10).map {
            Category(id = index++)
        }.apply { this@CategoryDataSourceImpl.list = this }
        delay(delayDurationInMillis)
        return list
    }

    override suspend fun add(name: String): Category? {
        val category = Category(index++)
        delay(delayDurationInMillis)
        return category
    }
}