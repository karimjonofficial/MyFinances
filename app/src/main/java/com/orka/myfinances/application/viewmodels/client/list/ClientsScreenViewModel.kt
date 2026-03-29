package com.orka.myfinances.application.viewmodels.client.list

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapperListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientUiModel
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class ClientsScreenViewModel(
    private val clientApi: ClientApi,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : MapperListViewModel<ClientApiModel, ClientUiModel>(
    loading = loading,
    failure = failure,
    get = { clientApi.getAll() },
    map = { it.toUiModel() },
    logger = logger
), ClientsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun add(
        name: String,
        lastName: String?,
        patronymic: String?,
        phone: String?,
        address: String?
    ) {
        launch {
            val request = AddClientRequest(
                firstName = name,
                lastName = lastName,
                patronymic = patronymic,
                phone = phone,
                address = address
            )
            if (clientApi.add(request)) initialize()
        }
    }

    override fun select(client: ClientUiModel) {
        launch {
            navigator.navigateToClient(client.id)
        }
    }
}