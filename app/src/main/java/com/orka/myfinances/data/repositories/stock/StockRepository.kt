package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.viewmodel.Chunk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class StockRepository(
    private val officeId: Id,
    private val api: StockApi,
    private val flow: MutableSharedFlow<StockEvent>
) : GetStockItemsByCategory, GetStockItemByProduct {
    val events: Flow<StockEvent> = flow.asSharedFlow()

    override suspend fun getByCategory(
        size: Int,
        pageIndex: Int,
        categoryId: Id,
        search: String?
    ): Chunk<StockItemDto>? {
        return api.getByCategory(
            branchId = officeId.value,
            categoryId = categoryId.value,
            page = pageIndex,
            pageSize = size,
            search = search
        )?.toChunk { it.toDto() }
    }

    override suspend fun getByProduct(productId: Id): StockItemDto? {
        return api.getByProduct(productId.value)?.toDto()
    }
}
