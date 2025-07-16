package com.orka.myfinances.fixtures.datasources.category

import com.orka.myfinances.datasources.CategoryDataSource
import com.orka.myfinances.models.ProductTemplate

class StubCategoryDataSource : CategoryDataSource {
    override suspend fun get(): List<ProductTemplate>? {
        return emptyList()
    }

    override suspend fun add(name: String): ProductTemplate? {
        return ProductTemplate(1)
    }
}