package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ProductRepository(private val apiService: ProductApiService) {

    private val events = MutableSharedFlow<ProductRepositoryEvent>(replay = 1)
    fun events(): Flow<ProductRepositoryEvent> = events

    suspend fun get(id: Id): List<Product>? {
        return apiService.get(id.value)
    }

    suspend fun get(): List<Product>? {
        return apiService.get()
    }

    suspend fun getById(id: Id): Product? {
        return apiService.getById(id.value)
    }

    suspend fun add(request: AddProductRequest): Product? {
        val product = apiService.add(request)
        if(product != null)
            events.emit(ProductRepositoryEvent.Add(product.id))
        return product
    }
}