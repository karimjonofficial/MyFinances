package com.orka.myfinances.fixtures.data.api.warehouse

import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseApiService
import com.orka.myfinances.fixtures.resources.models.folder.warehouses

class WarehouseApiServiceStub : WarehouseApiService {
    override suspend fun get(): List<Warehouse> {
        return emptyList()
    }

    override suspend fun get(id: Int): Warehouse {
        return warehouses[0]
    }
}
