package com.orka.myfinances.data.models.sale

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User
import kotlin.time.Instant

data class Sale(
    val id: Id,
    val user: User,
    val client: Client,
    val items: List<SaleItem>,
    val price: Int,
    val dateTime: Instant,
    val description: String?
)