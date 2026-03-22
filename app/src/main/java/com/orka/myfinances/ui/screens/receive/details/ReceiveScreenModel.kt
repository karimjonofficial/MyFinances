package com.orka.myfinances.ui.screens.receive.details

import com.orka.myfinances.data.models.User
import com.orka.myfinances.fixtures.resources.models.user1

data class ReceiveScreenModel(
    val user: User = user1,//TODO
    val price: String,
    val dateTime: String,
    val items: List<ReceiveItemModel>,
    val description: String? = null
)