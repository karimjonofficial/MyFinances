package com.orka.myfinances.runtime

import com.orka.myfinances.data.models.Session
import com.orka.myfinances.managers.SessionManager

interface SignedInRuntimeInitializer {
    fun initialize(session: Session, manager: SessionManager)
}