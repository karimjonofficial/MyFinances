package com.orka.myfinances.application.manager

import com.orka.myfinances.application.factories.Formatter
import com.orka.myfinances.application.factories.factory
import com.orka.myfinances.application.factories.httpClient
import com.orka.myfinances.application.factories.httpLogger
import com.orka.myfinances.application.models.CompanyApiModel
import com.orka.myfinances.data.api.auth.AuthenticationApi
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.ui.screens.host.LoginScreenViewModelFactory
import com.orka.myfinances.ui.screens.host.SelectOfficeScreenViewModelFactory
import com.orka.myfinances.ui.screens.host.viewmodel.HostScreenInteractor
import com.orka.myfinances.ui.screens.host.viewmodel.UiState
import kotlinx.coroutines.flow.asStateFlow

class UiManager(
    private val storage: LocalSessionStorage,
    private val printer: Printer,
    private val loading: UiText,
    private val failure: UiText,
    private val formatter: Formatter,
    logger: Logger,
) : SingleStateViewModel<UiState>(
    initialState = UiState.Initial,
    logger = logger
), HostScreenInteractor {
    private var store: Boolean = false
    private val client = httpClient(httpLogger(logger))
    private val authApi = AuthenticationApi(client)
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    private fun initialize() {
        launch {
            try {
                val sessionModel = storage.get()

                if (sessionModel != null) {
                    val credentials = sessionModel.credentials
                    when (val response = getCompany(client, credentials.access)) {
                        is ApiResponse.Success -> {
                            val company = (response.data as CompanyApiModel)
                            val office = getOffice(
                                client = client,
                                officeId = sessionModel.officeId,
                                access = credentials.access
                            )
                            if (office != null) {
                                val session = Session(
                                    credentials = credentials,
                                    officeId = Id(office.id),
                                    companyId = Id(company.id)
                                )
                                setStateSignedIn(session)
                            } else setStateNewUser(credentials)
                        }

                        ApiResponse.Unauthorized -> {
                            val newCredentials = getNewCredentials(credentials.refresh)
                            if (newCredentials != null) {
                                val company = ((getCompany(
                                    client = client,
                                    access = newCredentials.access
                                ) as ApiResponse.Success).data as CompanyApiModel)
                                val office = getOffice(
                                    client = client,
                                    officeId = sessionModel.officeId,
                                    access = newCredentials.access
                                )!!
                                val session = Session(
                                    credentials = newCredentials,
                                    officeId = Id(office.id),
                                    companyId = Id(company.id)
                                )
                                setStateSignedIn(session)
                            } else setStateGuest()
                        }

                        else -> onCompanyNotFound()
                    }
                } else setStateGuest()
            } catch (e: Exception) {
                setStateFailure(UiText.Str(e.message.toString()))
            }
        }
    }

    override fun refreshCredentials(credentials: Credentials) {
        launch {
            val stateValue = state.value
            if (stateValue is UiState.SignedIn) {
                val newCredentials = getNewCredentials(credentials.refresh)
                if (newCredentials != null)
                    setStateSignedIn(stateValue.session.copy(credentials = newCredentials))
                else setStateGuest()
            }
        }
    }

    override fun logout() {
        launch {
            storage.clear()
            setStateGuest()
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

    override fun setOffice(officeId: Id) {
        launch {
            when (val oldState = state.value) {
                is UiState.SignedIn -> {
                    val session = oldState.session.copy(officeId = officeId)
                    if (store) storage.store(session.toModel())
                    setStateSignedIn(session)
                }

                is UiState.NewUser -> {
                    val session = Session(
                        credentials = oldState.credentials,
                        officeId = officeId,
                        companyId = oldState.companyId
                    )
                    if (store) storage.store(session.toModel())
                    setStateSignedIn(session)
                }

                else -> setStateFailure(UiText.Str("Attempt to change office when state is changeable"))
            }
        }
    }

    private fun setStateFailure(message: UiText) {
        val state = UiState.Failure(message)
        setState(state)
    }

    private fun setStateGuest() {
        val factory = LoginScreenViewModelFactory(
            authApi = authApi,
            sessionManager = this,
            loading = loading,
            logger = logger
        )
        setState(UiState.Guest(factory))
    }

    private suspend fun setStateNewUser(credentials: Credentials) {
        val httpClient = httpClient(
            logger = httpLogger(logger),
            credentials = credentials,
            onUnauthorized = this::refreshCredentials
        )
        val response = getCompany(this.client, credentials.access)
        if (response is ApiResponse.Success) {
            val company = response.data as CompanyApiModel
            val companyId = Id(company.id)
            val factory = SelectOfficeScreenViewModelFactory(
                httpClient = httpClient,
                sessionManager = this,
                loading = loading,
                failure = failure,
                logger = logger
            )
            setState(UiState.NewUser(credentials, companyId, factory))
        } else onCompanyNotFound()
    }

    private fun setStateSignedIn(session: Session) {
        val httpClient = httpClient(
            logger = httpLogger(logger),
            credentials = session.credentials,
            onUnauthorized = this::refreshCredentials
        )
        val navigationManager = NavigationManager(logger)
        val factory = factory(
            session = session,
            printer = printer,
            logger = logger,
            httpClient = httpClient,
            sessionManager = this,
            navigator = navigationManager,
            formatter = formatter,
            loading = loading,
            failure = failure
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

        if (credentials != null) {
            storage.updateCredentials(credentials)
            return credentials
        } else {
            storage.clear()
            return null
        }
    }
}