package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.api.debt.models.request.AddDebtApiRequest
import com.orka.myfinances.data.api.debt.models.response.DebtApiModel
import com.orka.myfinances.data.api.user.UserApiModel
import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.dtos.user.UserDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.client.toDto

fun AddDebtRequest.toApiRequest(officeId: Id): AddDebtApiRequest {
    return AddDebtApiRequest(
        clientId = clientId.value,
        officeId = officeId.value,
        price = price,
        description = description,
        endDateTime = endDateTime
    )
}

fun DebtApiModel.toDto(): DebtDto {
    return DebtDto(
        id = id,
        user = user.toDto(),
        client = client.toDto(),
        completed = completed,
        price = price,
        notified = notified,
        dateTime = dateTime,
        endDateTime = endDateTime,
        description = description,
    )
}

fun UserApiModel.toDto(): UserDto {
    return UserDto(
        id = id,
        firstName = firstName,
        lastName = lastName,
        patronymic = patronymic,
        profession = profession,
        userName = userName,
        address = address,
        phone = phone,
    )
}
