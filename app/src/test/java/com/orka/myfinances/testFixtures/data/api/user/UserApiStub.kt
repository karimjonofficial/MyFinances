package com.orka.myfinances.testFixtures.data.api.user

import com.orka.myfinances.data.api.UserApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.UserModel
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.testFixtures.resources.models.user

class UserApiStub : UserApi {
    override suspend fun get(credential: Credential): UserModel {
        return user.toModel()
    }
}