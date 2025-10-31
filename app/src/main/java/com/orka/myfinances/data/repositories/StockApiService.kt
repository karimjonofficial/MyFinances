package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Warehouse

interface StockApiService {
    suspend fun get(id: Int): List<StockItem>?
    suspend fun get(): List<Warehouse>?
}
