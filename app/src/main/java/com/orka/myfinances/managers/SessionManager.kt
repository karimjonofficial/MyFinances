package com.orka.myfinances.managers

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id

interface SessionManager {
    fun open(credentials: Credentials)
    fun store(credentials: Credentials)
    fun setBranch(id: Id)
    fun refreshCredentials()
    fun logout()
}