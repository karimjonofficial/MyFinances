package com.orka.myfinances.lib.data.models

import com.orka.myfinances.data.models.Id

interface Person {
    val id: Id
    val firstName: String
    val lastName: String?
    val patronymic: String?
    val phone: String?
    val address: String?
}