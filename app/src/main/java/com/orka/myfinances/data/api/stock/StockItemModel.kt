package com.orka.myfinances.data.api.stock

import kotlin.time.Instant

data class StockItemModel(
    val productId: Int,
    val amount: Int,
    val dateTime: Instant
)
