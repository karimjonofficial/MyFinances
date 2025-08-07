package com.orka.myfinances.data.sources

import com.orka.myfinances.data.zipped.UserModel

interface UserDataSource {
    suspend fun get(): UserModel?
}