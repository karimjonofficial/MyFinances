package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.managers.SessionManager

interface HostScreenInteractor : SessionManager {
    fun initialize()
    fun refresh()
}