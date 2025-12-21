package com.orka.myfinances.data.repositories

data class AddReceiveRequest(
    val items: List<ReceiveItemModel>,
    val price: Int,
    val description: String? = null
)