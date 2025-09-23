package com.orka.myfinances.ui.managers.ui

import com.orka.myfinances.DialogManagerImpl
import com.orka.myfinances.NavigationManagerImpl
import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.factories.ViewModelProviderImpl
import com.orka.myfinances.fixtures.data.repositories.FolderRepositoryImpl
import com.orka.myfinances.lib.extensions.models.makeSession
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.ui.managers.session.SessionManager
import com.orka.myfinances.ui.managers.session.UiState
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.login.LoginScreenViewModel
import com.orka.myfinances.fixtures.data.repositories.TemplateRepositoryImpl
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
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
), SessionManager {
    val uiState = state.asStateFlow()

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

    private fun openSession(session: Session) {
        val folderRepository = FolderRepositoryImpl()
        val templateRepository = TemplateRepositoryImpl()
        val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger, defaultCoroutineContext)
        val initialBackStack = listOf(Destination.Home(homeScreenViewModel))
        val addTemplateScreenViewModel = AddTemplateScreenViewModel(templateRepository, defaultCoroutineContext)
        val templatesScreenViewModel = TemplatesScreenViewModel(templateRepository, logger, defaultCoroutineContext)
        templatesScreenViewModel.initialize()
        val provider = ViewModelProviderImpl(addTemplateScreenViewModel, templatesScreenViewModel, homeScreenViewModel)
        val dialogManager = DialogManagerImpl(provider, logger, defaultCoroutineContext)
        val navigationManager = NavigationManagerImpl(initialBackStack, provider, logger, defaultCoroutineContext)
        homeScreenViewModel.initialize()
        setState(UiState.SignedIn(session, dialogManager, navigationManager))
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