package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id

interface SessionManager {
    fun open(credentials: Credentials)
    fun store(credentials: Credentials)
    fun setOffice(officeId: Id)
    fun refreshCredentials(credentials: Credentials)
}