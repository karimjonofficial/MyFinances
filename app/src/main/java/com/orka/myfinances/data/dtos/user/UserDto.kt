package com.orka.myfinances.data.dtos.user

data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String?,
    val patronymic: String?,
    val profession: String?,
    val userName: String,
    val address: String?,
    val phone: String?,
)
