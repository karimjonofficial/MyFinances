package com.orka.myfinances.ui.screens

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SessionManager
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.datasources.CredentialDataSource
import com.orka.myfinances.models.Credential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

class LoginScreenViewModel(
    logger: Logger,
    private val dataSource: CredentialDataSource,
    private val manager: SessionManager,
    context: CoroutineContext = Dispatchers.Main
) : ViewModel<LoginScreenState>(
    initialState = LoginScreenState.Initial,
    context = context,
    logger = logger
) {
    val uiState = state.asStateFlow()

    fun authorize(username: String, password: String) = launch {
        launchAuthorization(username, password) {
            manager.createSession(it)
        }
    }

    fun authorizeAndRemember(username: String, password: String) = launch {
        launchAuthorization(username, password) {
            manager.storeSession(it)
        }
    }

    private suspend fun launchAuthorization(
        username: String,
        password: String,
        onSuccess: suspend (Credential) -> Unit
    ) {
        updateState { LoginScreenState.Loading }
        val credential = dataSource.get(username, password)
        if (credential != null) {
            onSuccess(credential)
            updateState { LoginScreenState.Initial }
        } else {
            updateState { LoginScreenState.Error }
        }
    }
}