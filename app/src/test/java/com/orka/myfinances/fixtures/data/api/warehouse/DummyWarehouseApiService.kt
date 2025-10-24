package com.orka.myfinances.fixtures.data.api.warehouse

import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseApiService

class DummyWarehouseApiService : WarehouseApiService {
    override suspend fun get(id: Int): Warehouse? {
        return null
    }

    override suspend fun get(): List<Warehouse>? {
        return null
    }
}