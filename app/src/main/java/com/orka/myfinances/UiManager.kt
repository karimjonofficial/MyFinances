package com.orka.myfinances

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SessionDataSource
import com.orka.myfinances.core.SessionManager
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.models.Credential
import com.orka.myfinances.fixtures.DummyCredentialDataSource
import com.orka.myfinances.models.Session
import com.orka.myfinances.ui.screens.LoginScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

class UiManager(
    logger: Logger,
    private val dataSource: SessionDataSource,
    context: CoroutineContext = Dispatchers.Main
) : ViewModel<UiState>(UiState.Initial, context, logger), SessionManager {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val session = dataSource.get()
        if (session == null) {
            val dataSource = DummyCredentialDataSource()
            val viewmodel = LoginScreenViewModel(
                logger = logger,
                dataSource = dataSource,
                manager = this,
                context = Dispatchers.Default
            )
            updateState { UiState.Guest(viewmodel) }
        }
        else updateState { UiState.SignedIn(session) }
    }

    override fun createSession(credential: Credential) {
        updateState { UiState.SignedIn(Session(credential)) }
    }

    override fun storeSession(credential: Credential) {
        launch {
            val session = Session(credential)
            updateState { UiState.SignedIn(session) }
            dataSource.store(session)
        }
    }
}