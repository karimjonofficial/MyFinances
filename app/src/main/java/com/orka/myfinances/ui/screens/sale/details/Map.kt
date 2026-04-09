package com.orka.myfinances.ui.screens.sale.details

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.client.list.toCardModel
import com.orka.myfinances.ui.screens.sale.details.interactor.Item
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenModel
import com.orka.myfinances.ui.screens.toCardModel

fun Sale.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): SaleScreenModel {
    return SaleScreenModel(
        id = id,
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        client = client.toCardModel(),
        items = items.map { it.toModel(formatDecimal) },
        clientId = this.client.id,
        user = user.toCardModel()
    )
}

fun SaleItem.toModel(formatDecimal: FormatDecimal): Item {
    return Item(
        title = product.title.name,
        supportingText = formatDecimal.formatDecimal(amount.toDouble())
    )
}