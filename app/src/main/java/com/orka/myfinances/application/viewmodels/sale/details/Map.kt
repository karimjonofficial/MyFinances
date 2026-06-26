package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.sale.details.interactor.Item
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenModel

fun SaleDto.toScreenModel(
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatTime: FormatTime,
    formatDecimal: FormatDecimal
): SaleScreenModel {
    return SaleScreenModel(
        id = Id(id),
        price = formatPrice.formatPrice(price.toDouble()),
        date = formatDate.formatDate(dateTime),
        time = formatTime.formatTime(dateTime),
        client = client.map(),
        user = user.toCardModel(),
        clientId = Id(client.id),
        items = items.map {
            Item(
                title = it.productName,
                supportingText = formatDecimal.formatDecimal(it.amount.toDouble())
            )
        },
        description = description
    )
}