package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.data.api.scoped.branch.add
import com.orka.myfinances.lib.data.api.scoped.branch.getChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.data.repositories.Chunk
import kotlinx.coroutines.flow.MutableSharedFlow

class SaleRepository(
    private val api: SaleApi,
    private val saleFlow: MutableSharedFlow<SaleEvent>,
    private val stockFlow: MutableSharedFlow<StockEvent>,
) : GetChunk<SaleDto>, SearchChunk<SaleDto>, GetById<SaleDto>, Insert<AddSaleRequest>, Add<SaleDto, AddSaleRequest> {

    override suspend fun getChunk(
        size: Int,
        page: Int
    ): Chunk<SaleDto>? {
        return searchChunk(size, page, null)
    }

    override suspend fun searchChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<SaleDto>? {
        return api.getChunk(
            size = size,
            page = page,
            search = query,
            orderBy = "-created_at"
        )?.let { chunk ->
            Chunk(
                count = chunk.count,
                pageIndex = chunk.pageIndex,
                nextPageIndex = chunk.nextPageIndex,
                previousPageIndex = chunk.previousPageIndex,
                results = chunk.results.map { it.toDto() }
            )
        }
    }

    override suspend fun getById(id: Id): SaleDto? {
        return api.getById(id)?.toDto()
    }

    override suspend fun add(request: AddSaleRequest): SaleDto? {
        val response = api.add(
            request = request,
            map = { officeId -> toApiRequest(officeId) }
        )
        if (response != null) {
            saleFlow.emit(SaleEvent)
            stockFlow.emit(StockEvent(Id(0)))
            return response.toDto()
        }
        return null
    }

    override suspend fun insert(request: AddSaleRequest): Boolean {
        return add(request) != null
    }
}
