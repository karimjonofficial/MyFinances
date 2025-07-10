package com.orka.myfinances.fixtures

import com.orka.myfinances.core.SessionManager
import com.orka.myfinances.models.Credential

class SpySessionManager : SessionManager {
    var createCalled = false
    var storeCalled = false

    override fun createSession(credential: Credential) {
        createCalled = true
    }

    override fun storeSession(credential: Credential) {
        storeCalled = true
    }
}