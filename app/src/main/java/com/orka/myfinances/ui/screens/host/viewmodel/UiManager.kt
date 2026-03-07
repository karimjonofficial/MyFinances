package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.api.office.map
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.fixtures.data.api.CredentialApiImpl
import com.orka.myfinances.fixtures.data.api.CredentialsModel
import com.orka.myfinances.fixtures.data.api.map
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.printer.pos.BluetoothPrinterImpl
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.login.LoginScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class UiManager(
    private val storage: LocalSessionStorage,
    private val printer: (CoroutineScope) -> BluetoothPrinterImpl,
    logger: Logger,
) : SingleStateViewModel<UiState>(
    initialState = UiState.Initial,
    logger = logger
), SessionManager {
    private var store: Boolean = false
    private val client = httpClient(httpLogger())

    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            delay(100)
            val sessionModel = storage.get()

            if (sessionModel != null) {
                val credentials = sessionModel.credentials
                val client = httpClient(
                    logger = httpLogger(),
                    credentials = credentials,
                    onUnauthorized = this::refreshCredentials
                    //TODO this has a side effect that I don't expect
                )
                val response = getCompany(client)
                when(response.status) {
                    HttpStatusCode.OK -> {
                        val company = response.body<CompanyApiModel>().map()
                        val office = getOffice(client, sessionModel.officeId)?.map(company)
                        if (office != null) {
                            val session = Session(
                                credentials = credentials,
                                office = office
                            )
                            setStateSignedIn(credentials, session)
                        } else setStateNewUser(credentials)
                    }

                    HttpStatusCode.Unauthorized -> {
                        val c = getNewCredentials(credentials.refresh)
                        if(c != null) {
                            val client = httpClient(httpLogger(), c, this::refreshCredentials)
                            val company = getCompany(client).body<CompanyApiModel>().map()
                            val office = getOffice(client, sessionModel.officeId)?.map(company)!!
                            val session = Session(
                                credentials = c,
                                office = office
                            )
                            setStateSignedIn(c, session)
                        } else setStateGuest()
                    }

                    else -> onCompanyNotFound()
                }
            } else setStateGuest()
        }
    }

    override fun refreshCredentials(credentials: Credentials) {
        launch {
            val stateValue = state.value
            if(stateValue is UiState.SignedIn) {
                val c = getNewCredentials(credentials.refresh)
                if(c != null)
                    setStateSignedIn(c, stateValue.session)
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
        val apiService = CredentialApiImpl(client)
        val viewModel = LoginScreenViewModel(logger, apiService, this)
        setState(UiState.Guest(viewModel))
    }

    private suspend fun setStateNewUser(credentials: Credentials) {
        val client = httpClient(
            logger = httpLogger(),
            credentials = credentials,
            onUnauthorized = this::refreshCredentials
        )
        val response = getCompany(client)
        if(response.status == HttpStatusCode.OK) {
            val company = response.body<CompanyApiModel>().map()
            val viewModel = SelectOfficeScreenViewModel(
                client = client,
                company = company,
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
            logger = httpLogger(),
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
        setStateFailure(UiText.Str("You don't belong to any company.\nConnect to the developers of this app"))
    }

    private suspend fun getCompany(client: HttpClient): HttpResponse {
        return client.get("users/me/company/")
    }

    private suspend fun getOffice(
        client: HttpClient,
        officeId: Int
    ): OfficeApiModel? {
        return OfficeApi(client).getById(officeId)
    }

    private fun httpLogger(): HttpLogger {
        return HttpLogger(logger)
    }

    private suspend fun getNewCredentials(refresh: String): Credentials? {
        val response = client.post(
            urlString = "auth/token/refresh/",
            block = {
                setBody(
                    buildJsonObject {
                        put("refresh", refresh)
                    }
                )
            }
        )

        if(response.status == HttpStatusCode.OK) {
            val model = response.body<CredentialsModel>()
            storage.updateCredentials(model)
            return model.map()
        } else {
            storage.clear()
            return null
        }
    }
}