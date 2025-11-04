package com.orka.myfinances.fixtures.data.api.product

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest

class DummyProductApiService : ProductApiService {
    override suspend fun add(request: AddProductRequest): Product? {
        return null
    }

    override suspend fun get(): List<Product>? {
        return null
    }

    override suspend fun getById(id: Int): Product {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): List<Product>? {
        return null
    }
}
