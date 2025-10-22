package com.orka.myfinances.factories

import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseApiService

class SpyWarehouseApiService : WarehouseApiService {
    var getCalled = false

    override suspend fun get(): List<Warehouse>? {
        getCalled = true
        return null
    }
}