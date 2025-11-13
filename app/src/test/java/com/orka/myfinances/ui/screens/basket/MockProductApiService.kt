package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.testLib.id1
import com.orka.myfinances.testLib.product1
import com.orka.myfinances.testLib.product2

class MockProductApiService : ProductApiService {
    override suspend fun add(request: AddProductRequest): Product? {
        return null
    }

    override suspend fun get(): List<Product>? {
        return null
    }

    override suspend fun getById(id: Int): Product {
        return if (id == id1.value) product1 else product2
    }

    override suspend fun get(id: Int): List<Product>? {
        return null
    }
}