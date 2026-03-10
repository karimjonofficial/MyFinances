package com.orka.myfinances.application.viewmodels.login

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.api.auth.AuthenticationApi
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.login.LoginScreenInteractor
import com.orka.myfinances.ui.screens.login.LoginScreenState
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel(
    logger: Logger,
    private val authApi: AuthenticationApi,
    private val manager: SessionManager
) : SingleStateViewModel<LoginScreenState>(
    initialState = LoginScreenState.Initial,
    logger = logger
), LoginScreenInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {}

    override fun authorize(username: String, password: String) {
        launch {
            authorize(username, password) {
                manager.open(it)
            }
        }
    }

    override fun authorizeAndRemember(username: String, password: String) {
        launch {
            authorize(username, password) {
                manager.store(it)
            }
        }
    }

    private suspend fun authorize(
        username: String,
        password: String,
        onSuccess: suspend (Credentials) -> Unit
    ) {
        setState(LoginScreenState.Loading)
        try {
            val credential = authApi.get(username, password)
            if (credential != null) {
                onSuccess(credential)
                setState(LoginScreenState.Initial)
            } else {
                setState(LoginScreenState.Error(UiText.Res(R.string.user_not_found)))
            }
        } catch (e: Exception) {
           setState(LoginScreenState.Error(UiText.Str(e.javaClass.name), true))
        }
    }
}