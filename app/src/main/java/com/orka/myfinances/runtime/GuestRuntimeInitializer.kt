package com.orka.myfinances.runtime

import com.orka.myfinances.managers.SessionManager

interface GuestRuntimeInitializer {
    fun initialize(manager: SessionManager)
}