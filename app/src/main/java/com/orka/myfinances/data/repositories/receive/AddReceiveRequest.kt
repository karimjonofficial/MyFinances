package com.orka.myfinances.data.repositories.receive

data class AddReceiveRequest(
    val items: List<AddReceiveRequestItem>,
    val price: Int,
    val comment: String? = null
)