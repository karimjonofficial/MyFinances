package com.orka.myfinances.data.dtos.receive

import com.orka.myfinances.data.dtos.user.UserDto
import kotlin.time.Instant

data class ReceiveDto(
    val id: Int,
    val user: UserDto,
    val branch: String,
    val price: Long,
    val dateTime: Instant,
    val items: List<ReceiveItemDto>,
    val description: String?,
)

data class ReceiveItemDto(
    val id: Int,
    val productName: String,
    val amount: Long,
)