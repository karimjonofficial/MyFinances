package com.orka.myfinances.data.repositories.receive

import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.toApiRequest
import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.viewmodel.Chunk
import kotlinx.coroutines.flow.MutableSharedFlow

class ReceiveRepository(
    private val officeId: Id,
    private val api: ReceiveApi,
    private val receiveFlow: MutableSharedFlow<ReceiveEvent>,
    private val stockFlow: MutableSharedFlow<StockEvent>
) : GetChunk<ReceiveDto>, GetById<ReceiveDto>, Insert<AddReceiveRequest> {

    override suspend fun getChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<ReceiveDto>? {
        return api.getChunk(
            branchId = officeId.value,
            page = page,
            pageSize = size,
            search = query
        )?.toChunk { it.toDto() }
    }

    override suspend fun getById(id: Id): ReceiveDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun insert(request: AddReceiveRequest): Boolean {
        val success = api.insert(request.toApiRequest(officeId))
        if (success) {
            receiveFlow.emit(ReceiveEvent)
            stockFlow.emit(StockEvent(request.categoryId))
        }
        return success
    }
}
