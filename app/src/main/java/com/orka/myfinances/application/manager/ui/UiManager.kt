package com.orka.myfinances.application.manager.ui

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.repositories.info.InfoRepository
import com.orka.myfinances.data.storages.credentials.CredentialsStorage
import com.orka.myfinances.data.storages.defaults.DefaultsStorage
import com.orka.myfinances.lib.ui.state.FailureStatus
import com.orka.myfinances.lib.viewmodel.single.SingleStateViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.runtime.GuestRuntimeInitializer
import com.orka.myfinances.runtime.NewUserRuntimeInitializer
import com.orka.myfinances.runtime.SignedInRuntimeInitializer
import com.orka.myfinances.ui.screens.host.viewmodel.HostScreenInteractor
import com.orka.myfinances.ui.screens.host.viewmodel.UiState
import com.orka.myfinances.validators.CredentialsValidator
import kotlinx.coroutines.flow.asStateFlow

class UiManager(
    private val credentialsStorage: CredentialsStorage,
    private val credentialsValidator: CredentialsValidator,
    private val defaultsStorage: DefaultsStorage,
    private val guestRuntimeInitializer: GuestRuntimeInitializer,
    private val newUserRuntimeInitializer: NewUserRuntimeInitializer,
    private val signedInRuntimeInitializer: SignedInRuntimeInitializer,
    private val infoRepository: InfoRepository,
    logger: Logger
) : SingleStateViewModel<UiState>(
    initialState = UiState.Initial,
    logger = logger
), SessionManager, HostScreenInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            loadSession()
        }
    }

    override fun refresh() {
        launch {
            setState(UiState.Loading)
            loadSession()
        }
    }

    override fun open(credentials: Credentials) {
        launch {
            val companyId = infoRepository.getCompanyId(credentials.access)
            setStateNewUser(credentials, companyId)
        }
    }

    override fun store(credentials: Credentials) {
        launch {
            val companyId = infoRepository.getCompanyId(credentials.access)
            credentialsStorage.set(credentials)
            setStateNewUser(credentials, companyId)
        }
    }

    override fun setBranch(id: Id) {
        launch {
            val state = state.value
            if(state !is UiState.NewUser && state !is UiState.SignedIn)
                setState(UiState.Failure())
            else {
                if(state is UiState.SignedIn) setState(UiState.Loading)
                val credentials = if(state is UiState.NewUser) state.credentials else (state as UiState.SignedIn).session.credentials
                val companyId = infoRepository.getCompanyId(credentials.access)
                defaultsStorage.setDefaultBranchId(id)
                val session = Session(
                    credentials = credentials,
                    branchId = id,
                    companyId = companyId
                )
                setStateSignedIn(session)
            }
        }
    }

    override fun refreshCredentials() {
        launch {
            setStateGuest()//TODO complete it
        }
    }

    override fun logout() {
        launch {
            credentialsStorage.clear()
            defaultsStorage.clear()
            setStateGuest()
        }
    }

    private fun setStateGuest() {
        guestRuntimeInitializer.initialize(this)
        setState(UiState.Guest)
    }

    private fun setStateNewUser(credentials: Credentials, companyId: Id) {
        newUserRuntimeInitializer.initialize(credentials, this)
        setState(UiState.NewUser(credentials, companyId))
    }

    private fun setStateSignedIn(session: Session) {
        signedInRuntimeInitializer.initialize(session, this)
        setState(UiState.SignedIn(session))
    }


    private suspend fun loadSession() {
        try {
            val credentials = credentialsStorage.get()
            if (credentials == null) setStateGuest()
            else {
                val validatedCredentials = credentialsValidator.validate(credentials)
                if (validatedCredentials == null) setStateGuest()
                else {
                    val branchId = defaultsStorage.getDefaultBranchId()
                    if (branchId == null) {
                        val companyId = infoRepository.getCompanyId(validatedCredentials.access)
                        setStateNewUser(validatedCredentials, companyId)
                    } else {
                        val companyId = infoRepository.getCompanyId(validatedCredentials.access)
                        val session = Session(validatedCredentials, branchId, companyId)
                        setStateSignedIn(session)
                    }
                }
            }
        } catch (e: Exception) {
            setState(UiState.Failure(FailureStatus.Exception(e)))
        }
    }
}