package com.orka.myfinances.ui.screens.debt.details

import com.orka.myfinances.application.viewmodels.sale.details.toCardModel
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.client.list.toModel

fun Debt.toScreenModel(
    formatPrice: FormatPrice,
    formatDate: FormatDate
): DebtScreenModel {
    return DebtScreenModel(
        price = formatPrice.formatPrice(price.toDouble()),
        startDate = formatDate.formatDate(dateTime),
        endDateTime = if (endDateTime != null) formatDate.formatDate(endDateTime) else null,
        notified = notified,
        client = client.toModel(),
        isOverdue = endDateTime != null && endDateTime < now(),
        id = this.id,
        user = user.toCardModel(),
        clientId = client.id,
        description = description
    )
}