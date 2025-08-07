package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.User

interface UserRepository {
    suspend fun get(): User?
}