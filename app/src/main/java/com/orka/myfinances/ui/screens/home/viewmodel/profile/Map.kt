package com.orka.myfinances.ui.screens.home.viewmodel.profile

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User

fun UserModel.map(): User {
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