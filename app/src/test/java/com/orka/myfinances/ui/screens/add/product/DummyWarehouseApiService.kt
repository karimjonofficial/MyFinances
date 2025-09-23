package com.orka.myfinances.ui.screens.add.product

import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseApiService

class DummyWarehouseApiService : WarehouseApiService {
    override suspend fun get(): List<Warehouse>? {
        TODO("Not yet implemented")
    }
}
