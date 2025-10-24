package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Warehouse

class WarehouseRepository(private val apiService: WarehouseApiService) {
    suspend fun get(id: Id): Warehouse? {
        val response = apiService.get(id.value)
        return response
    }

    suspend fun get(): List<Warehouse>? {
        return apiService.get()
    }
}