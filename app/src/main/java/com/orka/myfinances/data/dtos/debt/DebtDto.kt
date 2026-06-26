package com.orka.myfinances.data.dtos.debt

import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.dtos.user.UserDto
import kotlin.time.Instant

data class DebtDto(
    val id: Int,
    val user: UserDto,
    val client: ClientDto,
    val completed: Boolean,
    val price: Long,
    val notified: Boolean,
    val dateTime: Instant,
    val endDateTime: Instant?,
    val description: String?,
)
