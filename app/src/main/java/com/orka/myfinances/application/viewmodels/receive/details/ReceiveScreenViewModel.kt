package com.orka.myfinances.application.viewmodels.receive.details

import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.models.response.ReceiveApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenInteractor
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenModel
import kotlinx.coroutines.flow.asStateFlow

class ReceiveScreenViewModel(
    id: Id,
    private val receiveApi: ReceiveApi,
    private val navigator: Navigator,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<ReceiveApiModel, ReceiveScreenModel>(
    id = id,
    get = { receiveApi.getById(it) },
    map = { it.toScreenModel(formatPrice, formatDateTime, formatDecimal) },
    loading = loading,
    failure = failure,
    logger = logger
), ReceiveScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}