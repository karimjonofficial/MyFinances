package com.orka.myfinances.data.models.receive

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Branch
import com.orka.myfinances.data.models.User
import kotlin.time.Instant

data class Receive(
    val id: Id,
    val user: User,
    val branch: Branch,
    val items: List<ReceiveItem>,
    val price: Int,
    val dateTime: Instant,
    val description: String? = null
)