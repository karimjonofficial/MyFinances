package com.orka.myfinances.ui.screens.login

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.CredentialApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.lib.ui.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.managers.SessionManager
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.yield

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
        onSuccess: suspend (Credential) -> Unit
    ) {
        updateState { LoginScreenState.Loading }
        yield()
        val credential = apiService.get(username, password)
        if (credential != null) {
            onSuccess(credential)
            updateState { LoginScreenState.Initial }
        } else {
            updateState { LoginScreenState.Error }
        }
    }
}