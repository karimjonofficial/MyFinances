package com.orka.myfinances.application.viewmodels.profile

import com.orka.myfinances.data.api.user.UserApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User

fun UserApiModel.map(): User {
    return User(
        id = Id(id),
        firstName = firstName,
        userName = userName,
        lastName = lastName,
        patronymic = patronymic,
        phone = phone,
        address = address,
        profession = profession
    )
}

fun User.map(): UserApiModel {
    return UserApiModel(
        id = id.value,
        firstName = firstName,
        lastName = lastName,
        patronymic = patronymic,
        profession = profession,
        userName = userName,
        address = address,
        phone = phone
    )
}