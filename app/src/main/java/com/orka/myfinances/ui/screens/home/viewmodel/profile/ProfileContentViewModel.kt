package com.orka.myfinances.ui.screens.home.viewmodel.profile

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.User
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow

class ProfileContentViewModel(
    private val getOffices: Get<Office>,
    private val getUser: GetSingle<User>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            val offices = getOffices.get()
            val user = getUser.getSingle()
            if(offices != null && user != null) {
                setState(State.Success(ProfileContentModel(offices, user)))
            } else {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    fun history() {
        launch { navigator.navigateToHistory() }
    }

    fun settings() {
        launch { navigator.navigateToSettings() }
    }

    fun templates() {
        launch { navigator.navigateToTemplates() }
    }

    fun clients() {
        launch { navigator.navigateToClients() }
    }

    fun orders() {
        launch { navigator.navigateToOrders() }
    }

    fun debts() {
        launch { navigator.navigateToDebts() }
    }
}