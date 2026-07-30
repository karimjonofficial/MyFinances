package com.orka.myfinances.factories

import com.orka.myfinances.application.viewmodels.login.LoginScreenViewModel
import com.orka.myfinances.data.repositories.auth.Authenticator
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.managers.SessionManager

class LoginScreenViewModelFactory(
    private val authenticator: Authenticator,
    private val sessionManager: SessionManager,
    private val logger: Logger
) {
    fun get(): LoginScreenViewModel {
        return LoginScreenViewModel(
            authenticator = authenticator,
            manager = sessionManager,
            logger = logger
        )
    }
}