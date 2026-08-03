package com.orka.myfinances.ui.screens.sale.details

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.ui.screens.client.list.toCardModel
import com.orka.myfinances.ui.models.item.Item
import com.orka.myfinances.ui.models.screen.SaleScreenModel
import com.orka.myfinances.ui.map.toCardModel

fun Sale.toUiModel(): SaleScreenModel {
    return SaleScreenModel(
        id = id,
        price = price,
        dateTime = dateTime,
        client = client.toCardModel(),
        items = items.map { it.toModel() },
        clientId = this.client.id,
        user = user.toCardModel()
    )
}

fun SaleItem.toModel(): Item {
    return Item(
        title = product.title.name,
        supportingText = amount.toString()
    )
}
