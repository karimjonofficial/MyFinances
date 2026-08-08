package com.orka.myfinances.application.viewmodels.printers

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.printer.PrinterDto
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultPrinter
import com.orka.myfinances.data.repositories.defaults.SetDefaultPrinter
import com.orka.myfinances.data.repositories.printer.PrinterEvent
import com.orka.myfinances.data.repositories.printer.PrinterRepository
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.ui.PrinterUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

class DefaultPrinterViewModel(
    private val printerRepository: PrinterRepository,
    private val getPrinter: GetDefaultPrinter,
    private val set: SetDefaultPrinter,
    defaultsFlow: Flow<DefaultsEvent>,
    printerFlow: Flow<PrinterEvent>,
    logger: Logger
) : MapSingleViewModel<PrinterDto, PrinterUiModel>(
    get = {
        val id = getPrinter.getDefaultPrinter()
        if(id != null)
            printerRepository.getById(id)
        else null
    },
    map = {
        PrinterUiModel(
            title = it.model.name,
            description = it.model.address,
            dto = it
        )
    },
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        initialize()
        defaultsFlow.onEach {
            if(it == DefaultsEvent.Printer) initialize()
        }.launchIn(viewModelScope)

        printerFlow.onEach {
            initialize()
        }.launchIn(viewModelScope)
    }

    fun save(model: PrinterDto) {
        tryTransition { oldState ->
            delay(1.seconds)
            set.setDefaultPrinter(model.id)
            oldState
        }
    }
}