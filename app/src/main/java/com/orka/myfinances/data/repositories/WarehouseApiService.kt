package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.folder.Warehouse

interface WarehouseApiService {
    suspend fun get(): List<Warehouse>?
}
