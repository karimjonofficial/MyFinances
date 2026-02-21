package com.orka.myfinances.ui.screens.sale.viewmodel

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.ui.components.ClientCardModel

data class SaleUiModel(
    val price: String,
    val dateTime: String,
    val client: ClientCardModel,
    val items: List<Item>,
    val sale: Sale
)