package com.orka.myfinances.fixtures.data.api.warehouse

import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseApiService

class EmptyWarehouseApiServiceStub : WarehouseApiService {
    override suspend fun get(): List<Warehouse>? {
        return null
    }
}
