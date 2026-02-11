package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.UserApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.UserModel
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.extensions.models.toModel

class UserApiImpl : UserApi {
    override suspend fun get(credential: Credential): UserModel {
        return user1.toModel()
    }
}