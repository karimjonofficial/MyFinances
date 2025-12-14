package com.orka.myfinances.testFixtures.resources.models

import com.orka.myfinances.data.models.User
import com.orka.myfinances.testFixtures.resources.address
import com.orka.myfinances.testFixtures.resources.firstName
import com.orka.myfinances.testFixtures.resources.lastName
import com.orka.myfinances.testFixtures.resources.patronymic
import com.orka.myfinances.testFixtures.resources.phone
import com.orka.myfinances.testFixtures.resources.username

val user = User(
    id = id1,
    firstName = firstName,
    userName = username,
    company = company,
    lastName = lastName,
    patronymic = patronymic,
    phone = phone,
    address = address
)