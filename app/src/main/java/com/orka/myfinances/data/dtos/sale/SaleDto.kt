package com.orka.myfinances.data.dtos.sale

import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.dtos.user.UserDto
import kotlin.time.Instant

data class SaleDto(
    val id: Int,
    val client: ClientDto,
    val user: UserDto,
    val items: List<SaleItemDto>,
    val dateTime: Instant,
    val price: Long,
    val description: String? = null,
)

data class SaleItemDto(
    val id: Int,
    val productName: String,
    val amount: Long,
)
