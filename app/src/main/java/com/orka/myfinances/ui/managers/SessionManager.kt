package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Office

interface SessionManager {
    fun initialize()
    fun open(credentials: Credentials)
    fun store(credentials: Credentials)
    fun setOffice(credentials: Credentials, office: Office)
    fun refreshCredentials(credentials: Credentials)
}