package com.orka.myfinances.application.viewmodels.debt.details

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.ui.models.screen.DebtScreenModel

fun DebtDto.toScreenModel(): DebtScreenModel {
    return DebtScreenModel(
        price = price.toInt(),
        startDate = dateTime,
        endDateTime = endDateTime,
        notified = notified,
        client = client.map(),
        isOverdue = endDateTime != null && endDateTime < now(),
        id = Id(id),
        user = user.toCardModel(),
        clientId = Id(client.id),
        completed = completed,
        description = description
    )
}
