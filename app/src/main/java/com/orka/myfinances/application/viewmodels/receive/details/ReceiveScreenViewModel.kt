package com.orka.myfinances.application.viewmodels.receive.details

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenInteractor
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenModel
import kotlinx.coroutines.flow.asStateFlow

class ReceiveScreenViewModel(
    private val id: Id,
    private val receiveApi: ReceiveApi,
    private val navigator: Navigator,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val loading: UiText,
    logger: Logger
) : SingleStateViewModel<State<ReceiveScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), ReceiveScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            setState(State.Loading(loading))
            val receive = receiveApi.getById(id.value)
            if (receive != null) {
                setState(State.Success(receive.toUiModel(formatPrice, formatDateTime, formatDecimal)))
            } else {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}