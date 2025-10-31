package com.orka.myfinances.fixtures.data.api.warehouse

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.StockApiService

class EmptyStockApiServiceStub : StockApiService {
    override suspend fun get(id: Int): List<StockItem>? {
        return null
    }

    override suspend fun get(): List<Warehouse>? {
        return null
    }
}
