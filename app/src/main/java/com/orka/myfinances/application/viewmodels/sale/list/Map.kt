package com.orka.myfinances.application.viewmodels.sale.list

import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.sale.list.SaleCardModel
import com.orka.myfinances.ui.screens.sale.list.SaleUiModel

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