package com.orka.myfinances.application.viewmodels.login

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.auth.AuthenticationApi
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.login.LoginScreenInteractor
import com.orka.myfinances.ui.screens.login.LoginScreenModel
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel(
    private val authApi: AuthenticationApi,
    private val manager: SessionManager,
    private val loading: UiText,
    logger: Logger,
) : SingleStateViewModel<State<LoginScreenModel>>(
    initialState = State.Success(LoginScreenModel(serverError = false, textFieldError = false)),
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
        setState(State.Loading(loading))

        try {
            val credential = authApi.get(username, password)
            if (credential != null) {
                onSuccess(credential)
            } else setState(
                State.Failure(
                    value = LoginScreenModel(serverError = false, textFieldError = true),
                    error = UiText.Str("User is not found")
                )
            )//TODO extract string resource
        } catch (e: Exception) {
            setState(
                State.Failure(
                    value = LoginScreenModel(serverError = true, textFieldError = false),
                    error = UiText.Str(e.message.toString())
                )
            )
        }
    }
}