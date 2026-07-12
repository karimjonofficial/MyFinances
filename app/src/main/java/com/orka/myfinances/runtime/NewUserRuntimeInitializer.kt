package com.orka.myfinances.runtime

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.ui.managers.SessionManager

interface NewUserRuntimeInitializer {
    fun initialize(credentials: Credentials, manager: SessionManager)
}