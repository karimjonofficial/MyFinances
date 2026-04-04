package com.orka.myfinances.application.viewmodels

import com.orka.myfinances.data.api.user.UserApiModel
import com.orka.myfinances.ui.components.UserCardModel

fun UserApiModel.toCardModel(): UserCardModel {
    val f = if(firstName.isEmpty()) "" else firstName[0]
    val l = if(lastName.isNullOrEmpty()) "" else lastName[0]
    val shortName = f.toString() + l.toString()
    return UserCardModel(
        shortName = shortName,
        fullName = "$firstName $lastName",
        phone = phone
    )
}