package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.api.receive.models.request.AddReceiveApiRequest
import com.orka.myfinances.data.api.receive.models.request.AddReceiveApiRequestItem
import com.orka.myfinances.data.api.receive.models.response.ReceiveApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.history.components.ReceiveCardModel
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveUiModel

fun ReceiveApiModel.map(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatTime: FormatTime
): ReceiveUiModel {
    return ReceiveUiModel(
        id = Id(id),
        model = ReceiveCardModel(
            title = items.joinToString { it.product.title.name },
            price = formatPrice.formatPrice(price.toDouble()),
            size = "${formatDecimal.formatDecimal(items.size.toDouble())} items",
            dateTime = formatTime.formatTime(dateTime)
        ),
        instant = dateTime,
    )
}

fun AddReceiveRequest.map(officeId: Int): AddReceiveApiRequest {
    return AddReceiveApiRequest(
        items = items.map { it.map() },
        officeId = officeId,
        price = price,
    )
}

fun AddReceiveRequestItem.map(): AddReceiveApiRequestItem {
    return AddReceiveApiRequestItem(
        productTitleId = productTitleId.value,
        price = price,
        salePrice = salePrice,
        amount = amount,
        description = description
    )
}