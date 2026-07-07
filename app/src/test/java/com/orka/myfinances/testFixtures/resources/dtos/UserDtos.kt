package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.user.UserDto

val userDto1 = UserDto(
    id = 1,
    firstName = "John",
    lastName = "Doe",
    patronymic = null,
    profession = "Developer",
    userName = "johndoe",
    address = "Main St 1",
    phone = "123456789"
)

val userDtos = listOf(userDto1)
