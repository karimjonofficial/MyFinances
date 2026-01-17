package com.orka.myfinances.testFixtures.data.api.warehouse

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.api.StockApiService

class DummyStockApiService : StockApiService {
    override suspend fun get(id: Int): List<StockItem>? {
        return null
    }

    override suspend fun get(): List<Category>? {
        return null
    }
}