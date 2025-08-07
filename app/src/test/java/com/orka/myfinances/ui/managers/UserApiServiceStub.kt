package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.UserModel
import com.orka.myfinances.lib.extensions.toModel
import com.orka.myfinances.testLib.user

class UserApiServiceStub : UserApiService {
    override suspend fun get(credential: Credential): UserModel? {
        return user.toModel()
    }
}