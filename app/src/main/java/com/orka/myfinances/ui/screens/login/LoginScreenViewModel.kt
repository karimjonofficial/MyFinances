package com.orka.myfinances.ui.screens.login

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.ui.managers.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.yield
import kotlin.coroutines.CoroutineContext

class LoginScreenViewModel(
    logger: Logger,
    private val apiService: CredentialApiService,
    private val manager: SessionManager,
    defaultCoroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel<LoginScreenState>(
    initialState = LoginScreenState.Initial,
    defaultCoroutineContext = defaultCoroutineContext,
    logger = logger
) {
    val uiState = state.asStateFlow()

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