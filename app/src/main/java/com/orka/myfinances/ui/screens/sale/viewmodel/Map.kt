package com.orka.myfinances.ui.screens.sale.viewmodel

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.clients.viewmodel.toModel

fun Sale.toUiModel(formatPrice: FormatPrice, formatDateTime: FormatDateTime, formatDecimal: FormatDecimal): SaleUiModel {
    return SaleUiModel(
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        client = client.toModel(),
        items = items.map { it.toModel(formatDecimal) },
        sale = this
    )
}

fun SaleItem.toModel(formatDecimal: FormatDecimal): Item {
    return Item(name = product.title.name, amount = formatDecimal.formatDecimal(amount.toDouble()))
}