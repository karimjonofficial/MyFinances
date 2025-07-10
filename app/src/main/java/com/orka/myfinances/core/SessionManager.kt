package com.orka.myfinances.core

import com.orka.myfinances.models.Credential

interface SessionManager {
    fun createSession(credential: Credential)
    fun storeSession(credential: Credential)
}