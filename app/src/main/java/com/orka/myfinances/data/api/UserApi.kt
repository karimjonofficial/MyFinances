package com.orka.myfinances.data.api

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.UserModel

interface UserApi {
    suspend fun get(credential: Credential): UserModel?
}