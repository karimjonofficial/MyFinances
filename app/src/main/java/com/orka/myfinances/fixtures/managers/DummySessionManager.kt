package com.orka.myfinances.fixtures.managers

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.ui.managers.SessionManager

class DummySessionManager : SessionManager {
    override fun open(credential: Credential) {}
    override fun store(credential: Credential) {}
    override fun setOffice(credential: Credential, office: Office) {}
}