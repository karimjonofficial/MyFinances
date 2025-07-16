package com.orka.myfinances.fixtures.datasources.category

import com.orka.myfinances.datasources.CategoryDataSource
import com.orka.myfinances.models.ProductTemplate

class EmptyCategoryDataSource : CategoryDataSource {
    override suspend fun get(): List<ProductTemplate>? {
        return null
    }

    override suspend fun add(name: String): ProductTemplate? {
        return null
    }
}