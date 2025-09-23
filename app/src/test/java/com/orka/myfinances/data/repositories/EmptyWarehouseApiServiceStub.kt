package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.folder.Warehouse

class EmptyWarehouseApiServiceStub : WarehouseApiService {
    override suspend fun get(): List<Warehouse>? {
        return null
    }
}
