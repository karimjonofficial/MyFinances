package com.orka.myfinances.fixtures.data.api.user

import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.UserModel

class EmptyUserApiServiceStub : UserApiService {
    override suspend fun get(credential: Credential): UserModel? {
        return null
    }
}
