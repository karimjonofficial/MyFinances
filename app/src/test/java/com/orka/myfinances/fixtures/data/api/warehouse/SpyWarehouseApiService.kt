package com.orka.myfinances.fixtures.data.api.warehouse

import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseApiService

class SpyWarehouseApiService : WarehouseApiService {
    var getCalled = false

    override suspend fun get(id: Int): Warehouse? {
        getCalled = true
        return null
    }

    override suspend fun get(): List<Warehouse>? {
        getCalled = true
        return null
    }
}