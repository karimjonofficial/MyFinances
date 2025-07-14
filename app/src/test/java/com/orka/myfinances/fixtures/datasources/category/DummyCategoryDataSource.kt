package com.orka.myfinances.fixtures.datasources.category

import com.orka.myfinances.datasources.CategoryDataSource
import com.orka.myfinances.models.Category

class DummyCategoryDataSource : CategoryDataSource {
    override suspend fun get(): List<Category>? {
        return null
    }

    override suspend fun add(name: String): Category? {
        TODO("Not yet implemented")
    }
}