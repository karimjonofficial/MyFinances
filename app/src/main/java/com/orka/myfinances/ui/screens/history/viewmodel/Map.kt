package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.data.api.sale.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatNames
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.history.components.ReceiveCardModel
import com.orka.myfinances.ui.screens.history.components.SaleCardModel

fun SaleApiModel.map(
    priceFormatter: FormatPrice,
    formatDateTime: FormatDateTime
): SaleUiModel {
    return SaleUiModel(
        model = SaleCardModel(
            title = items.joinToString { it.product.title.name },
            price = priceFormatter.formatPrice(price.toDouble()),
            size = "${items.size} items",
            dateTime = formatDateTime.formatDateTime(dateTime)
        ),
        id = Id(id)
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
        id = this.id,
        model = this.toModel(format, priceFormatter, dateFormatter, timeFormatter),
        instant = dateTime
    )
}
