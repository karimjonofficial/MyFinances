package com.orka.myfinances.application.viewmodels.sale.list

import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.SaleCardModel
import com.orka.myfinances.ui.models.ui.SaleUiModel

fun SaleDto.map(): SaleUiModel {
    return SaleUiModel(
        model = SaleCardModel(
            title = items.joinToString { it.productName },
            price = price.toInt(),
            size = items.size,
            dateTime = dateTime
        ),
        id = Id(id)
    )
}
