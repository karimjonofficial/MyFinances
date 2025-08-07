package com.orka.myfinances.data.sources

import com.orka.myfinances.data.models.ProductTemplate

interface ProductTemplateDataSource {
    suspend fun get(): List<ProductTemplate>?
    suspend fun add(name: String): ProductTemplate?
}