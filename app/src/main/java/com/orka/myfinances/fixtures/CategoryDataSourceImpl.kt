package com.orka.myfinances.fixtures

import com.orka.myfinances.datasources.CategoryDataSource
import com.orka.myfinances.models.Category

class CategoryDataSourceImpl : CategoryDataSource {
    override suspend fun get(): List<Category>? {
        return null
    }
}