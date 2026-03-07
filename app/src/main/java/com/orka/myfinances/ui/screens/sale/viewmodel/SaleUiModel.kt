package com.orka.myfinances.ui.screens.sale.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.components.ClientCardModel
import com.orka.myfinances.ui.components.UserCardModel

data class SaleUiModel(
    val id: Id,
    val price: String,
    val dateTime: String,
    val client: ClientCardModel,
    val user: UserCardModel,
    val clientId: Id,
    val items: List<Item>,
    val description: String? = null
)