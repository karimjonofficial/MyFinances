package com.orka.myfinances.fixtures

import com.orka.myfinances.core.SessionManager
import com.orka.myfinances.models.Credential

class DummySessionManager : SessionManager {
    override fun createSession(credential: Credential) {

    }

    override fun storeSession(credential: Credential) {

    }
}