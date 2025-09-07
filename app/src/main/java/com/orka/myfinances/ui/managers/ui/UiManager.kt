package com.orka.myfinances.ui.managers.ui

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.fixtures.data.repositories.FolderRepositoryImpl
import com.orka.myfinances.lib.extensions.makeSession
import com.orka.myfinances.lib.extensions.toModel
import com.orka.myfinances.ui.managers.session.SessionManager
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.login.LoginScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

class UiManager(
    logger: Logger,
    private val storage: LocalSessionStorage,
    private val provider: ApiProvider,
    context: CoroutineContext = Dispatchers.Main
) : ViewModel<UiState>(
    initialState = UiState.Initial,
    defaultCoroutineContext = context,
    logger = logger
), SessionManager, DialogManager {
    private val _dialogState = MutableStateFlow<DialogState?>(null)

    val uiState = state.asStateFlow()
    override val dialogState = _dialogState.asStateFlow()

    fun initialize() = launch {
        val sessionModel = storage.get()

        if (sessionModel != null) {
            val session = sessionModel.toSession()
            openSession(session)
        } else {
            val apiService = provider.getCredentialApiService()
            val viewModel = LoginScreenViewModel(logger, apiService, this, defaultCoroutineContext)
            setState(UiState.Guest(viewModel))
        }
    }

    override fun open(credential: Credential) {
        launch {
            val session = getSession(credential)
            if (session != null) openSession(session)
        }
    }

    override fun store(credential: Credential) {
        launch {
            val session = getSession(credential)

            if (session != null) {
                storage.store(session.toModel())
                openSession(session)
            }
        }
    }


    override fun show(dialog: DialogState) {
        //TODO the problem with concurrent state changes is not solved yet
        launch { _dialogState.update { dialog } }
    }

    override fun hide() {
        launch { _dialogState.update { null } }
    }

    private fun openSession(session: Session) {
        val repository = FolderRepositoryImpl()
        val viewModel = HomeScreenViewModel(logger, repository, defaultCoroutineContext)
        viewModel.initialize()
        setState(UiState.SignedIn(session, viewModel))
    }
    private suspend fun getSession(credential: Credential): Session? {
        val userApiService = provider.getUserApiService()
        val companyApiService = provider.getCompanyApiService()
        val companyOfficeApiService = provider.getCompanyOfficeApiService()

        val userModel = userApiService.get(credential)
        val companyModel = companyApiService.get(credential)
        val companyOfficeModel = companyOfficeApiService.get(credential)

        return if (userModel != null && companyModel != null && companyOfficeModel != null) {
            makeSession(credential, userModel, companyOfficeModel, companyModel)
        } else null
    }
}