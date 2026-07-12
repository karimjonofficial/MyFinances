package com.orka.myfinances.application.manager.session

import com.orka.myfinances.data.models.Credentials
import kotlinx.coroutines.flow.StateFlow

interface SessionManager {
    val sessionState: StateFlow<SessionState>

    fun initialize()
    fun setCredentials(credentials: Credentials) {}
}