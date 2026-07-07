package com.orka.myfinances.data.repositories.user

import com.orka.myfinances.data.api.user.UserApi
import com.orka.myfinances.data.dtos.user.UserDto

class UserRepository(
    private val api: UserApi
) : GetMe {
    override suspend fun getMe(): UserDto? {
        return api.getMe()?.let {
            UserDto(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                patronymic = it.patronymic,
                profession = it.profession,
                userName = it.userName,
                address = it.address,
                phone = it.phone
            )
        }
    }
}
