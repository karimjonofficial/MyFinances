package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.repositories.Chunk

fun interface GetOrdersChunk {
    suspend fun getOrdersChunk(
        size: Int,
        page: Int,
        completed: Boolean,
        query: String?
    ): Chunk<OrderDto>?
}
