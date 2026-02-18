package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatNames
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.history.SaleCardModel
import com.orka.myfinances.ui.screens.history.components.ReceiveCardModel

fun Sale.toModel(
    format: FormatNames,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): SaleCardModel {
    return SaleCardModel(
        title = format.formatNames(items.map { it.product.title }),
        price = priceFormatter.formatPrice(price.toDouble()),
        size = "${items.size} items",
        dateTime = "${dateFormatter.formatDate(dateTime)} ${timeFormatter.formatTime(dateTime)}"
    )
}

fun Sale.toUiModel(
    format: FormatNames,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): SaleUiModel {
    return SaleUiModel(
        model = this.toModel(format, priceFormatter, dateFormatter, timeFormatter),
        sale = this
    )
}

fun Receive.toModel(
    format: FormatNames,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): ReceiveCardModel {
    return ReceiveCardModel(
        title = format.formatNames(items.map { it.product.title }),
        price = priceFormatter.formatPrice(price.toDouble()),
        size = "${items.size} items",
        dateTime = "${dateFormatter.formatDate(dateTime)} ${timeFormatter.formatTime(dateTime)}"
    )
}

fun Receive.toUiModel(
    format: FormatNames,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): ReceiveUiModel {
    return ReceiveUiModel(
        model = this.toModel(format, priceFormatter, dateFormatter, timeFormatter),
        receive = this
    )
}
