package com.orka.myfinances.data.repositories.receive

import com.orka.myfinances.data.models.Id

data class AddReceiveRequest(
    val categoryId: Id,//TODO Remove this terrible field
    val items: List<AddReceiveRequestItem>,
    val price: Int,
    val comment: String? = null
)