package com.orka.myfinances.application.viewmodels.defaults.client

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultClient
import com.orka.myfinances.data.repositories.defaults.SetDefaultClient
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.settings.defaults.client.SelectDefaultClientInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DefaultClientViewModel(
    private val getDefaultClient: GetDefaultClient,
    private val setDefaultClient: SetDefaultClient,
    flow: Flow<DefaultsEvent>,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleViewModel<Id?, Id?>(
    get = { getDefaultClient.getDefaultClientId() },
    map = { it },
    logger = logger
), SelectDefaultClientInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        flow.onEach {
            if (it is DefaultsEvent.Client)
                initialize()
        }.launchIn(viewModelScope)
    }

    override fun select(id: Id) {
        tryTransition { _ ->
            setDefaultClient.setDefaultClientId(id)
            navigator.back()
            State.Success(id)
        }
    }
}
