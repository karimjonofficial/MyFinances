package com.orka.myfinances.application.viewmodels.client.list

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.client.AddClientApiRequest
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.viewmodel.MapperListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientModel
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class ClientsScreenViewModel(
    private val clientApi: ClientApi,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : MapperListViewModel<ClientApiModel, ClientModel>(
    loading = loading,
    failure = failure,
    get = { clientApi.getAll() },
    map = { it.toUiModel() },
    logger = logger
), ListViewModel<ClientModel>, ClientsScreenInteractor {
    override val uiState = state.asStateFlow()

    override fun add(
        name: String,
        lastName: String?,
        phone: String?,
        address: String?
    ) {
        launch {
            val request = AddClientApiRequest(
                firstName = name,
                lastName = lastName,
                phone = phone,
                address = address
            )
            if (clientApi.add(request)) initialize()
        }
    }

    override fun select(client: ClientModel) {
        launch {
            navigator.navigateToClient(client.id)
        }
    }
}