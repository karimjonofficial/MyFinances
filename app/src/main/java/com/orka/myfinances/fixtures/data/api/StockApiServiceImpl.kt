package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.api.StockApiService
import com.orka.myfinances.fixtures.resources.models.folder.warehouses
import com.orka.myfinances.fixtures.resources.models.stockItem1

class StockApiServiceImpl : StockApiService {
    override suspend fun get(id: Int): List<StockItem> {
        return listOf(stockItem1)
    }

    override suspend fun get(): List<Category> {
        return warehouses
    }
}