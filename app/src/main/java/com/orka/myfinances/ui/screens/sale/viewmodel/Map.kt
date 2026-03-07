package com.orka.myfinances.ui.screens.sale.viewmodel

import com.orka.myfinances.data.api.sale.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.components.UserCardModel
import com.orka.myfinances.ui.screens.client.viewmodel.map
import com.orka.myfinances.ui.screens.clients.viewmodel.toModel
import com.orka.myfinances.ui.screens.home.viewmodel.profile.UserApiModel

fun Sale.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): SaleUiModel {
    return SaleUiModel(
        id = id,
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        client = client.toModel(),
        items = items.map { it.toModel(formatDecimal) },
        clientId = this.client.id,
        user = user.map()
    )
}

fun SaleItem.toModel(formatDecimal: FormatDecimal): Item {
    return Item(
        title = product.title.name,
        supportingText = formatDecimal.formatDecimal(amount.toDouble())
    )
}

fun SaleApiModel.map(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): SaleUiModel {
    return SaleUiModel(
        id = Id(id),
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        client = client.map().toModel(),
        user = user.map(),
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

fun UserApiModel.map(): UserCardModel {
    return UserCardModel(
        shortName = "${firstName[0]}${lastName?.get(0)}",
        fullName = "$firstName $lastName",
        phone = phone
    )
}

fun User.map(): UserCardModel {
    return UserCardModel(
        shortName = "${firstName[0]}${lastName?.get(0)}",
        fullName = "$firstName $lastName",
        phone = phone
    )
}