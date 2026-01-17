package com.orka.myfinances.testFixtures.data.api.warehouse

import com.orka.myfinances.data.api.StockApiService
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category

class StockApiServiceStub : StockApiService {
    override suspend fun get(): List<Category> {
        return emptyList()
    }

    override suspend fun get(id: Int): List<StockItem> {
        return emptyList()
    }
}
