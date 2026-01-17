package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.api.StockApiService
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category

class StockRepository(private val apiService: StockApiService) {
    suspend fun get(id: Id): List<StockItem>? {
        val response = apiService.get(id.value)
        return response
    }

    suspend fun get(): List<Category>? {
        return apiService.get()
    }
}