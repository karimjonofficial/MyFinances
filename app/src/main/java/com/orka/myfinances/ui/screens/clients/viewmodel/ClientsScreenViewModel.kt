package com.orka.myfinances.ui.screens.clients.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.client.AddClientApiRequest
import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.viewmodel.MapperListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class ClientsScreenViewModel(
    private val client: HttpClient,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : MapperListViewModel<ClientApiModel, ClientModel>(
    loading = loading,
    failure = failure,
    get = {
        try {
            val response = client.get("clients/")
            if (response.status == HttpStatusCode.OK) {
                response.body<List<ClientApiModel>>()
            } else null
        } catch (_: Exception) {
            null
        }
    },
    map = { it.toUiModel() },
    logger = logger
), ListViewModel<ClientModel> {
    override val uiState = state.asStateFlow()

    fun add(name: String, lastName: String?, phone: String?, address: String?) {
        launch {
            try {
                val request = AddClientApiRequest(
                    firstName = name,
                    lastName = lastName,
                    phone = phone,
                    address = address
                )
                val response = client.post(
                    urlString = "clients/",
                    block = { setBody(request) }
                )
                if (response.status == HttpStatusCode.Created) initialize()
            } catch (_: Exception) {
                // Handle error
            }
        }
    }

    fun select(client: ClientModel) {
        launch {
            navigator.navigateToClient(client.id)
        }
    }
}