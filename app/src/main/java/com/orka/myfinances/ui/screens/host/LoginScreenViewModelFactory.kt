package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.application.viewmodels.login.LoginScreenViewModel
import com.orka.myfinances.data.api.auth.AuthenticationApi
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.managers.SessionManager

class LoginScreenViewModelFactory(
    private val authApi: AuthenticationApi,
    private val sessionManager: SessionManager,
    private val loading: UiText,
    private val logger: Logger
) {
    fun get(): LoginScreenViewModel {
        return LoginScreenViewModel(
            authApi = authApi,
            manager = sessionManager,
            loading = loading,
            logger = logger
        )
    }
}