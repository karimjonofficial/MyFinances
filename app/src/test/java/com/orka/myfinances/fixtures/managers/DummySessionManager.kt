package com.orka.myfinances.fixtures.managers

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.ui.managers.session.SessionManager

class DummySessionManager : SessionManager {
    override fun open(credential: Credential) {

    }

    override fun store(credential: Credential) {

    }
}