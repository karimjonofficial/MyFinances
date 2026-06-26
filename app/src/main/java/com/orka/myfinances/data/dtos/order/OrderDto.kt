package com.orka.myfinances.data.dtos.order

import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.dtos.office.OfficeDto
import com.orka.myfinances.data.dtos.user.UserDto
import kotlin.time.Instant

data class OrderDto(
    val id: Int,
    val user: UserDto,
    val client: ClientDto,
    val branch: OfficeDto,
    val price: Long,
    val endDateTime: Instant?,
    val completed: Boolean,
    val completedDateTime: Instant?,
    val description: String?,
    val items: List<OrderItemDto>,
    val createdAt: Instant,
)

data class OrderItemDto(
    val id: Int,
    val amount: Long,
    val product: OrderProductDto,
)

data class OrderProductDto(
    val id: Int,
    val name: String,
    val price: Long,
    val salePrice: Long,
)
