package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Warehouse

class StockRepository(private val apiService: StockApiService) {
    suspend fun get(id: Id): List<StockItem>? {
        val response = apiService.get(id.value)
        return response
    }

    suspend fun get(): List<Warehouse>? {
        return apiService.get()
    }
}