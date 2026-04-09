package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.sale.details.interactor.Item
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenModel

fun SaleApiModel.toScreenModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): SaleScreenModel {
    return SaleScreenModel(
        id = Id(id),
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        client = client.map(),
        user = user.toCardModel(),
        clientId = Id(client.id),
        items = items.map {
            Item(
                title = it.product.title.name,
                supportingText = formatDecimal.formatDecimal(it.amount.toDouble())
            )
        },
        description = description
    )
}