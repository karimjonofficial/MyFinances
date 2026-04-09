package com.orka.myfinances.ui.screens

import com.orka.myfinances.data.models.User
import com.orka.myfinances.ui.components.UserCardModel

fun User.toCardModel(): UserCardModel {
    return UserCardModel(
        shortName = "${firstName[0]}${lastName?.get(0)}",
        fullName = "$firstName $lastName",
        phone = phone
    )
}