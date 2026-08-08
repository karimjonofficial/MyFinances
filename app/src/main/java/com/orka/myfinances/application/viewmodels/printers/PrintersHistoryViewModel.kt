package com.orka.myfinances.application.viewmodels.printers

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.data.repositories.printer.PrinterRepository
import com.orka.myfinances.data.dtos.printer.PrinterDto
import com.orka.myfinances.data.repositories.printer.PrinterEvent
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.viewmodel.mappers.DatabaseExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.list.map.MapListViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.ui.PrinterUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PrintersHistoryViewModel(
    private val repository: PrinterRepository,
    flow: Flow<PrinterEvent>,
    logger: Logger
) : MapListViewModel<PrinterDto, PrinterUiModel>(
    get = { repository.getAll() },
    map = { PrinterUiModel(title = it.model.name, description = it.model.address, dto = it) },
    groupBy = { it.model.name.stickyHeaderKey() },
    exceptionMapper = DatabaseExceptionMapper(),
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        initialize()
        flow.onEach { initialize() }.launchIn(viewModelScope)
    }
}