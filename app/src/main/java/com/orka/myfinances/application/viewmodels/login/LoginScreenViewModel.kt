package com.orka.myfinances.application.viewmodels.login

import com.orka.myfinances.logger.Logger
import com.orka.myfinances.data.repositories.auth.Authenticator
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.BaseViewModel
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.ui.screens.login.LoginScreenInteractor
import com.orka.myfinances.ui.screens.login.LoginScreenModel
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel(
    private val authenticator: Authenticator,
    private val manager: SessionManager,
    logger: Logger,
) : BaseViewModel<LoginScreenModel>(
    produceInitialState = { State.Success(LoginScreenModel()) },
    logger = logger
), LoginScreenInteractor {
    val uiState = state.asStateFlow()

    override fun authorize(username: String, password: String) {
        tryTransition { oldState ->
            authorize(username, password, oldState) {
                manager.open(it)
            }
        }
    }

    override fun authorizeAndRemember(username: String, password: String) {
        tryTransition { oldState ->
            authorize(username, password, oldState) {
                manager.store(it)
            }
        }
    }

    private suspend fun authorize(
        username: String,
        password: String,
        oldState: State<LoginScreenModel>,
        onSuccess: suspend (Credentials) -> Unit
    ): State<LoginScreenModel> {
        val credential = authenticator.authenticate(username, password)
        return if (credential != null) {
            setState(State.Success(LoginScreenModel()))
            onSuccess(credential)
            oldState
        } else State.Failure(
            value = LoginScreenModel(textFieldError = true),
            type = UserNotFound
        )
    }
}