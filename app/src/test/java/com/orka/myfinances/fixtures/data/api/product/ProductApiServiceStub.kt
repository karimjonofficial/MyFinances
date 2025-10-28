package com.orka.myfinances.fixtures.data.api.product

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.testLib.product
import com.orka.myfinances.testLib.products

class ProductApiServiceStub : ProductApiService {
    override suspend fun add(request: AddProductRequest): Product? {
        return product
    }

    override suspend fun get(): List<Product> {
        return products
    }

    override suspend fun get(id: Int): List<Product>? {
        return products
    }
}