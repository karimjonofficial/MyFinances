package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.models.Credential

interface SessionManager {
    fun open(credential: Credential)
    fun store(credential: Credential)
}