package com.orka.myfinances.ui.screens.debt.details

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.ui.screens.client.list.toCardModel
import com.orka.myfinances.ui.map.toCardModel
import com.orka.myfinances.ui.models.screen.DebtScreenModel

fun Debt.toScreenModel(): DebtScreenModel {
    return DebtScreenModel(
        price = price,
        startDate = dateTime,
        endDateTime = endDateTime,
        notified = notified,
        client = client.toCardModel(),
        isOverdue = endDateTime != null && endDateTime < now(),
        id = this.id,
        user = user.toCardModel(),
        clientId = client.id,
        completed = completed,
        description = description
    )
}
