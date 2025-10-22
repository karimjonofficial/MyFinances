package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest

class DummyProductApiService : ProductApiService {
    override suspend fun add(request: AddProductRequest): Product? {
        return null
    }
}
