package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.UserModel
import com.orka.myfinances.fixtures.user
import com.orka.myfinances.lib.extensions.toModel

class UserApiServiceImpl : UserApiService {
    override suspend fun get(credential: Credential): UserModel? {
        return user.toModel()
    }
}