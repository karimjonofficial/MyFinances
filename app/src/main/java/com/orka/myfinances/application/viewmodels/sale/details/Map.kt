package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.components.UserCardModel
import com.orka.myfinances.ui.screens.client.list.toModel
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

fun User.toCardModel(): UserCardModel {
    return UserCardModel(
        shortName = "${firstName[0]}${lastName?.get(0)}",
        fullName = "$firstName $lastName",
        phone = phone
    )
}

fun Sale.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): SaleScreenModel {
    return SaleScreenModel(
        id = id,
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        client = client.toModel(),
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