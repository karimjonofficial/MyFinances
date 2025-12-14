package com.orka.myfinances.testFixtures.data.api.product

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.testFixtures.resources.models.product.product1
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.testFixtures.resources.models.product.products

class SpyProductApiService : ProductApiService {
    var addCalled = false
    var getCalled = false

    override suspend fun add(request: AddProductRequest): Product {
        addCalled = true
        return product1
    }

    override suspend fun get(): List<Product> {
        getCalled = true
        return products
    }

    override suspend fun getById(id: Int): Product {
        return product1
    }

    override suspend fun get(id: Int): List<Product> {
        getCalled = true
        return products
    }
}
