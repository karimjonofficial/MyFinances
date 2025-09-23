package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.folder.Warehouse

class WarehouseApiServiceStub : WarehouseApiService {
    override suspend fun get(): List<Warehouse> {
        return emptyList()
    }
}
