package com.orka.myfinances.fixtures.data.sources.category

import com.orka.myfinances.data.sources.ProductTemplateDataSource
import com.orka.myfinances.data.models.ProductTemplate
import com.orka.myfinances.testLib.id

class StubProductTemplateDataSource : ProductTemplateDataSource {
    override suspend fun get(): List<ProductTemplate>? {
        return emptyList()
    }

    override suspend fun add(name: String): ProductTemplate? {
        return ProductTemplate(id, "name", emptyList())
    }
}