package com.orka.myfinances.data.api

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category

interface StockApiService {
    suspend fun get(id: Int): List<StockItem>?
    suspend fun get(): List<Category>?
}