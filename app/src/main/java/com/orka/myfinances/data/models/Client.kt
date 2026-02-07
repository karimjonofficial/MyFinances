package com.orka.myfinances.data.models

import com.orka.myfinances.lib.data.models.Person

data class Client(
    override val id: Id,
    override val firstName: String,
    override val lastName: String? = null,
    override val patronymic: String? = null,
    override val phone: String? = null,
    override val address: String? = null
) : Person