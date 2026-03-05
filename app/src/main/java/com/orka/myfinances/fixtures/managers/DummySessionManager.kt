package com.orka.myfinances.fixtures.managers

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.ui.managers.SessionManager

class DummySessionManager : SessionManager {
    override fun open(credentials: Credentials) {}
    override fun store(credentials: Credentials) {}
    override fun setOffice(credentials: Credentials, office: Office) {}
    override fun refreshCredentials(credentials: Credentials) {}
}