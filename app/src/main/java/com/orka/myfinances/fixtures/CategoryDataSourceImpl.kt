package com.orka.myfinances.fixtures

import com.orka.myfinances.datasources.CategoryDataSource
import com.orka.myfinances.models.ProductTemplate
import kotlinx.coroutines.delay

class CategoryDataSourceImpl : CategoryDataSource {
    private var index = 0
    private var list: List<ProductTemplate>? = null

    override suspend fun get(): List<ProductTemplate>? {
        val list = this.list ?: (1..10).map {
            ProductTemplate(id = index++)
        }.apply { this@CategoryDataSourceImpl.list = this }
        delay(delayDurationInMillis)
        return list
    }

    override suspend fun add(name: String): ProductTemplate? {
        val productTemplate = ProductTemplate(index++)
        delay(delayDurationInMillis)
        return productTemplate
    }
}