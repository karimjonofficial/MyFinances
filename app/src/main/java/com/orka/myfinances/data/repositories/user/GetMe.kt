package com.orka.myfinances.data.repositories.user

import com.orka.myfinances.data.dtos.user.UserDto

fun interface GetMe {
    suspend fun getMe(): UserDto?
}
