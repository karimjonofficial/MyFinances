package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id

fun interface GetStockItemByProduct {
    suspend fun getByProduct(productId: Id): StockItemDto?
}
