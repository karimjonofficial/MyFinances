package com.orka.myfinances.ui.screens.login

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.credential.AuthRequest
import com.orka.myfinances.data.api.credential.CredentialApi
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.managers.SessionManager
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel(
    logger: Logger,
    private val apiService: CredentialApi,
    private val manager: SessionManager
) : SingleStateViewModel<LoginScreenState>(
    initialState = LoginScreenState.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {}

    fun authorize(username: String, password: String) = launch {
        launchAuthorization(username, password) {
            manager.open(it)
        }
    }

    fun authorizeAndRemember(username: String, password: String) = launch {
        launchAuthorization(username, password) {
            manager.store(it)
        }
    }

    private suspend fun launchAuthorization(
        username: String,
        password: String,
        onSuccess: suspend (Credentials) -> Unit
    ) {
        setState(LoginScreenState.Loading)
        try {
            val credential = apiService.get(AuthRequest(username, password))
            if (credential != null) {
                onSuccess(credential)
                setState(LoginScreenState.Initial)
            } else {
                setState(LoginScreenState.Error(UiText.Res(R.string.user_not_found)))
            }
        } catch (e: Exception) {
            logger.log("LoginScreenViewModel", e.message.toString())
           setState(LoginScreenState.Error(UiText.Str(e.javaClass.name), true))
        }
    }
}