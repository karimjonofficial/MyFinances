package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.StockApiService
import com.orka.myfinances.fixtures.resources.models.folder.warehouses

class StockApiServiceImpl : StockApiService {
    override suspend fun get(id: Int): List<StockItem> {
        return emptyList()
    }

    override suspend fun get(): List<Warehouse> {
        return warehouses
    }
}