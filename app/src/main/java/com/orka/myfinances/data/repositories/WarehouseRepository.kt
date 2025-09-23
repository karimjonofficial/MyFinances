package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.folder.Warehouse

class WarehouseRepository(private val apiService: WarehouseApiService) {
    suspend fun get(): List<Warehouse>? {
        val response = apiService.get()
        return response
    }
}