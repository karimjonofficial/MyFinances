package com.orka.myfinances.data.models

import com.orka.myfinances.lib.data.models.Person

data class User(
    override val id: Id,
    override val firstName: String,
    val userName: String,
    val company: Company,
    override val lastName: String? = null,
    override val patronymic: String? = null,
    override val phone: String? = null,
    override val address: String? = null,
    val profession: String? = null
) : Person