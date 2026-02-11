package com.orka.myfinances.testFixtures.data.api.user

import com.orka.myfinances.data.api.UserApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.UserModel

class DummyUserApi : UserApi {
    override suspend fun get(credential: Credential): UserModel? {
        return null
    }
}