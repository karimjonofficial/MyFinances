package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseApiService
import com.orka.myfinances.fixtures.resources.models.folder.warehouses

class WarehouseApiServiceImpl : WarehouseApiService {
    override suspend fun get(): List<Warehouse>? {
        return warehouses
    }
}