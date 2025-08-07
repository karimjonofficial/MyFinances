package com.orka.myfinances.lib.extensions

import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.zipped.UserModel

fun User.toModel(): UserModel {
    return UserModel(
        id = this.id.value,
        firstName = this.firstName,
        userName = this.userName,
        companyId = this.company.id.value,
        lastName = this.lastName,
        patronymic = this.patronymic,
    )
}