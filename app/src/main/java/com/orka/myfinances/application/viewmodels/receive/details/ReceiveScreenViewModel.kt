package com.orka.myfinances.application.viewmodels.receive.details

import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.format.FormatDateTime
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenInteractor
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenModel
import kotlinx.coroutines.flow.asStateFlow

class ReceiveScreenViewModel(
    id: Id,
    getById: GetById<ReceiveDto>,
    private val navigator: Navigator,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<ReceiveDto, ReceiveScreenModel>(
    id = id,
    get = getById,
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