package com.orka.myfinances.data.api

import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest

interface ProductApiService {
    suspend fun add(request: AddProductRequest): Product?
    suspend fun get(): List<Product>?
}