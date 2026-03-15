package com.orka.myfinances.application.manager

import com.orka.myfinances.R
import com.orka.myfinances.application.factories.factory
import com.orka.myfinances.application.factories.httpClient
import com.orka.myfinances.application.factories.httpLogger
import com.orka.myfinances.application.viewmodels.login.LoginScreenViewModel
import com.orka.myfinances.application.viewmodels.office.SelectOfficeScreenViewModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.auth.AuthenticationApi
import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.api.office.map
import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.printer.pos.BluetoothPrinterImpl
import com.orka.myfinances.ui.screens.host.viewmodel.HostScreenInteractor
import com.orka.myfinances.ui.screens.host.viewmodel.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow

class UiManager(
    private val storage: LocalSessionStorage,
    private val printer: (CoroutineScope) -> BluetoothPrinterImpl,
    logger: Logger,
) : SingleStateViewModel<UiState>(
    initialState = UiState.Initial,
    logger = logger
), HostScreenInteractor {
    private var store: Boolean = false
    private val client = httpClient(httpLogger(logger))
    private val authApi = AuthenticationApi(client)

    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                if(state.value !is UiState.Initial) setState(UiState.Loading)
                val sessionModel = storage.get()

                if (sessionModel != null) {
                    val credentials = sessionModel.credentials
                    when (val response = getCompany(client, credentials.access)) {
                        is ApiResponse.Success -> {
                            val company = (response.data as Company)
                            val office =
                                getOffice(client, sessionModel.officeId, credentials.access)?.map(company)
                            if (office != null) {
                                val session = Session(
                                    credentials = credentials,
                                    office = office
                                )
                                setStateSignedIn(credentials, session)
                            } else setStateNewUser(credentials)
                        }

                        ApiResponse.Unauthorized -> {
                            val newCredentials = getNewCredentials(credentials.refresh)
                            if (newCredentials != null) {
                                val client = httpClient(
                                    logger = httpLogger(logger = logger),
                                    credentials = newCredentials,
                                    onUnauthorized = this::refreshCredentials
                                )
                                val company = ((getCompany(
                                    client,
                                    newCredentials.access
                                ) as ApiResponse.Success).data as Company)
                                val office = getOffice(
                                    client,
                                    sessionModel.officeId,
                                    newCredentials.access
                                )?.map(company)!!
                                val session = Session(
                                    credentials = newCredentials,
                                    office = office
                                )
                                setStateSignedIn(newCredentials, session)
                            } else setStateGuest()
                        }

                        else -> onCompanyNotFound()
                    }
                } else setStateGuest()
            } catch(e: Exception) {
                setStateFailure(UiText.Str(e.message.toString()))
            }
        }
    }

    override fun refreshCredentials(credentials: Credentials) {
        launch {
            val stateValue = state.value
            if(stateValue is UiState.SignedIn) {
                val newCredentials = getNewCredentials(credentials.refresh)
                if(newCredentials != null)
                    setStateSignedIn(newCredentials, stateValue.session)
                else setStateGuest()
            }
        }
    }

    override fun open(credentials: Credentials) {
        launch {
            setStateNewUser(credentials)
        }
    }

    override fun store(credentials: Credentials) {
        launch {
            store = true
            setStateNewUser(credentials)
        }
    }

    override fun setOffice(credentials: Credentials, office: Office) {
        launch {
            val session = Session(
                office = office,
                credentials = credentials
            )
            if(store) storage.store(session.toModel())
            setStateSignedIn(credentials, session)
        }
    }

    private fun setStateFailure(message: UiText) {
        val state = UiState.Failure(message)
        setState(state)
    }

    private fun setStateGuest() {
        val apiService = AuthenticationApi(client)
        val viewModel = LoginScreenViewModel(logger, apiService, this)
        setState(UiState.Guest(viewModel))
    }

    private suspend fun setStateNewUser(credentials: Credentials) {
        val client = httpClient(
            logger = httpLogger(logger),
            credentials = credentials,
            onUnauthorized = this::refreshCredentials
        )
        val response = getCompany(this.client, credentials.access)
        if(response is ApiResponse.Success) {
            val viewModel = SelectOfficeScreenViewModel(
                officeApi = OfficeApi(client),
                company = (response.data as Company),
                sessionManager = this,
                credentials = credentials,
                loading = UiText.Res(R.string.loading),
                failure = UiText.Res(R.string.failure),
                logger = logger
            )
            setState(UiState.NewUser(credentials, viewModel))
        } else onCompanyNotFound()
    }

    private fun setStateSignedIn(
        credentials: Credentials,
        session: Session
    ) {
        val httpClient = httpClient(
            logger = httpLogger(logger),
            credentials = credentials,
            onUnauthorized = this::refreshCredentials
        )
        val navigationManager = NavigationManager(logger)
        val factory = factory(
            session = session,
            printer = printer(CoroutineScope(Dispatchers.Default)),
            logger = logger,
            httpClient = httpClient,
            navigator = navigationManager
        )
        setState(UiState.SignedIn(session, navigationManager, factory))
    }

    private fun onCompanyNotFound() {
        setStateFailure(
            message = UiText.Str(
                value = "You don't belong to any company." +
                        "\nConnect to the developers of this app"
            )
        )
    }

    private suspend fun getNewCredentials(refresh: String): Credentials? {
        val credentials = authApi.refresh(refresh)

        if(credentials != null) {
            storage.updateCredentials(credentials)
            return credentials
        } else {
            storage.clear()
            return null
        }
    }
}



