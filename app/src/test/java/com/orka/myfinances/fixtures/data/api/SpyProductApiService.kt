package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.testLib.product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest

class SpyProductApiService : ProductApiService {
    var addCalled = false

    override suspend fun add(request: AddProductRequest): Product? {
        addCalled = true
        return product
    }
}
