package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Office

interface SessionManager {
    fun open(credential: Credential)
    fun store(credential: Credential)
    fun setOffice(credential: Credential, office: Office)
}