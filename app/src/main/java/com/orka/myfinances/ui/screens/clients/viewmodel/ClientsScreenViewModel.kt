package com.orka.myfinances.ui.screens.clients.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapperListViewModel
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow

class ClientsScreenViewModel(
    get: Get<Client>,
    private val add: Add<Client, AddClientRequest>,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : MapperListViewModel<Client, ClientModel>(
    loading = loading,
    failure = failure,
    repository = get,
    map = { it.toUiModel() },
    logger = logger
), ListViewModel<ClientModel> {
    override val uiState = state.asStateFlow()

    fun add(name: String, lastName: String?, phone: String?, address: String?) {
        launch {
            val request = AddClientRequest(
                name = name,
                lastName = lastName,
                phone = phone,
                address = address
            )
            if(add.add(request) != null) initialize()
        }
    }

    fun select(client: Client) {
        launch {
            navigator.navigateToClient(client)
        }
    }
}