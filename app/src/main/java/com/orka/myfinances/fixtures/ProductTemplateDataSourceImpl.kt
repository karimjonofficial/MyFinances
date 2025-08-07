package com.orka.myfinances.fixtures

import com.orka.myfinances.data.models.ProductTemplate
import com.orka.myfinances.data.sources.ProductTemplateDataSource

class ProductTemplateDataSourceImpl : ProductTemplateDataSource {
    override suspend fun get(): List<ProductTemplate>? {
        return null
    }

    override suspend fun add(name: String): ProductTemplate? {
        throw NotImplementedError()
    }
}