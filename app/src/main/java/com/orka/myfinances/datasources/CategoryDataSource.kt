package com.orka.myfinances.datasources

import com.orka.myfinances.models.ProductTemplate

interface CategoryDataSource {
    suspend fun get(): List<ProductTemplate>?
    suspend fun add(name: String): ProductTemplate?
}