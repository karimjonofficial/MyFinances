package com.orka.myfinances.runtime

import com.orka.myfinances.ui.managers.SessionManager

interface GuestRuntimeInitializer {
    fun initialize(manager: SessionManager)
}