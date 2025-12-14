package com.orka.myfinances.testFixtures.data.api.warehouse

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.api.StockApiService

class SpyStockApiService : StockApiService {
    var getCalled = false

    override suspend fun get(id: Int): List<StockItem>? {
        getCalled = true
        return null
    }

    override suspend fun get(): List<Warehouse>? {
        getCalled = true
        return null
    }
}