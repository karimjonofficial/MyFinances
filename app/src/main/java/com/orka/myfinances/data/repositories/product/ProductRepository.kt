package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest

class ProductRepository(private val apiService: ProductApiService) {
    suspend fun add(request: AddProductRequest): Product? {
        return apiService.add(request)
    }
}
