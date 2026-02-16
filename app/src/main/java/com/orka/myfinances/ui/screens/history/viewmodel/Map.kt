package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.ui.screens.history.SaleCardModel
import com.orka.myfinances.ui.screens.history.components.ReceiveCardModel

fun Sale.toModel(): SaleCardModel {
    return SaleCardModel(
        title = items.map { it.product.title.name }.joinToString { ", " },
        price = "$$price",
        size = "${items.size} items",
        dateTime = "$dateTime"
    )
}

fun Sale.toUiModel(): SaleUiModel {
    return SaleUiModel(
        model = this.toModel(),
        sale = this
    )
}

fun Receive.toModel(): ReceiveCardModel {
    return ReceiveCardModel(
        title = items.map { it.product.title.name }.joinToString { ", " },
        price = "$$price",
        size = "${items.size} items",
        dateTime = "$dateTime"
    )
}

fun Receive.toUiModel(): ReceiveUiModel {
    return ReceiveUiModel(
        model = this.toModel(),
        receive = this
    )
}