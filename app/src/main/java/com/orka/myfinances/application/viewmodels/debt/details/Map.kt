package com.orka.myfinances.application.viewmodels.debt.details

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.data.api.debt.models.response.DebtApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.debt.details.DebtScreenModel
import com.orka.myfinances.application.viewmodels.sale.details.map


fun DebtApiModel.map(
    formatPrice: FormatPrice,
    formatDate: FormatDate
): DebtScreenModel {
    return DebtScreenModel(
        price = formatPrice.formatPrice(price.toDouble()),
        startDate = formatDate.formatDate(dateTime),
        endDateTime = if (endDateTime != null) formatDate.formatDate(endDateTime) else null,
        notified = notified,
        client = client.map(),
        isOverdue = endDateTime != null && endDateTime < now(),
        id = Id(id),
        user = user.map(),
        clientId = Id(client.id),
        description = description
    )
}