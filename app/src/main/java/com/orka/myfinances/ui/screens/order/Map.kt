package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.data.models.order.Order

fun Order.toModel(): OrderCardModel {
    return OrderCardModel(
        title = "${client.firstName} ${client.lastName}",
        dateTime = dateTime.toString(),
        size = "${items.size} items",
        price = "$${price}",
        completed = completed
    )
}

fun Order.toUiModel(): OrderUiModel {
    return OrderUiModel(
        order = this,
        model = this.toModel()
    )
}