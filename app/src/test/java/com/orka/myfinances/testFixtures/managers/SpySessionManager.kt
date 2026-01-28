package com.orka.myfinances.testFixtures.managers

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.ui.managers.SessionManager

class SpySessionManager : SessionManager {
    var createCalled = false
    var storeCalled = false

    override fun open(credential: Credential) {
        createCalled = true
    }

    override fun store(credential: Credential) {
        storeCalled = true
    }
}