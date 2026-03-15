package com.orka.myfinances.application.viewmodels.sale

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.data.api.sale.SaleApiModel
import com.orka.myfinances.data.api.user.UserApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.components.UserCardModel
import com.orka.myfinances.ui.screens.client.list.toModel
import com.orka.myfinances.ui.screens.sale.viewmodel.Item
import com.orka.myfinances.ui.screens.sale.viewmodel.SaleUiModel

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
        client = client.map(),
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
    val f = if(firstName.isEmpty()) "" else firstName[0]
    val l = if(lastName.isNullOrEmpty()) "" else lastName[0]
    val shortName = f.toString() + l.toString()
    return UserCardModel(
        shortName = shortName,
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