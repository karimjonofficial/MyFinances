package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.viewmodel.Chunk

interface GetStockItemsByCategory {
    suspend fun getByCategory(
        size: Int,
        pageIndex: Int,
        categoryId: Id,
        search: String? = null
    ): Chunk<StockItemDto>?
}
