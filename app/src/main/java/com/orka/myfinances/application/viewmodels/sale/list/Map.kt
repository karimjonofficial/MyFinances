package com.orka.myfinances.application.viewmodels.sale.list

import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.sale.list.SaleCardModel
import com.orka.myfinances.ui.screens.sale.list.SaleUiModel

fun SaleApiModel.map(
    priceFormatter: FormatPrice,
    formatTime: FormatTime
): SaleUiModel {
    return SaleUiModel(
        model = SaleCardModel(
            title = items.joinToString { it.product.title.name },
            price = priceFormatter.formatPrice(price.toDouble()),
            size = "${items.size} items",
            dateTime = formatTime.formatTime(dateTime)
        ),
        id = Id(id)
    )
}