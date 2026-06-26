package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.order.toCompleteOrderApiRequest
import com.orka.myfinances.data.api.order.toSetEndDateApiRequest
import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.viewmodel.Chunk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.time.Instant

class OrderRepository(
    private val officeId: Id,
    private val api: OrderApi,
    private val flow: MutableSharedFlow<OrderEvent>
) : GetChunk<OrderDto>, GetOrdersChunk, GetById<OrderDto>, Insert<AddOrderRequest>, CompleteOrder, SetEndDate {
    override suspend fun getChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<OrderDto>? {
        return getOrdersChunk(size, page, false, query)
    }

    override suspend fun getOrdersChunk(
        size: Int,
        page: Int,
        completed: Boolean,
        query: String?
    ): Chunk<OrderDto>? {
        return api.getChunk(
            branchId = officeId.value,
            page = page,
            pageSize = size,
            search = query,
            completed = completed,
            ordering = "-created_at"
        )?.toChunk { it.toDto() }
    }

    override suspend fun getById(id: Id): OrderDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun insert(request: AddOrderRequest): Boolean {
        val success = api.insert(request.toApiRequest(officeId))
        if (success) {
            flow.emit(OrderEvent)
        }
        return success
    }

    override suspend fun complete(id: Id): Boolean {
        val success = api.patch(id.value, id.toCompleteOrderApiRequest())
        if (success) {
            flow.emit(OrderEvent)
        }
        return success
    }

    override suspend fun setEndDate(id: Id, endDateTime: Instant): Boolean {
        val success = api.patch(id.value, id.toSetEndDateApiRequest(endDateTime))
        if (success) {
            flow.emit(OrderEvent)
        }
        return success
    }
}
