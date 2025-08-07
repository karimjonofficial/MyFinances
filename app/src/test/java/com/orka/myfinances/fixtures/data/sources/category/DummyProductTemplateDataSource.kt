package com.orka.myfinances.fixtures.data.sources.category

import com.orka.myfinances.data.sources.ProductTemplateDataSource
import com.orka.myfinances.data.models.ProductTemplate

class DummyProductTemplateDataSource : ProductTemplateDataSource {
    override suspend fun get(): List<ProductTemplate>? {
        return null
    }

    override suspend fun add(name: String): ProductTemplate? {
        return null
    }
}