package com.orka.myfinances.application.viewmodels.printers

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.R
import com.orka.myfinances.printer.PrinterModel
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.list.map.MapListViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.printer.PrinterStatus
import com.orka.myfinances.ui.models.ui.BluetoothPrinterUiModel
import com.orka.myfinances.ui.screens.settings.printers.PrintersInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

class BluetoothPrintersViewModel(
    get: Get<PrinterModel>,
    private val flow: StateFlow<PrinterStatus>,
    private val printer: Printer,
    logger: Logger
) : MapListViewModel<PrinterModel, BluetoothPrinterUiModel>(
    get = {
        delay(1.seconds)
        get.getAll()
    },
    exceptionMapper = ExceptionMapper.Default(),
    map = {
        val status = flow.value
        if(status is PrinterStatus.Connected) {
            BluetoothPrinterUiModel(
                title = it.name,
                description = it.address,
                leadingIconRes = if(it.address == status.printer.address) R.drawable.bluetooth_connected else null,
                model = it
            )
        } else {
            BluetoothPrinterUiModel(
                title = it.name,
                description = it.address,
                model = it
            )
        }
    },
    groupBy = { it.name.stickyHeaderKey() },
    logger = logger
), PrintersInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        flow.onEach { initialize() }.launchIn(viewModelScope)
    }

    override fun connect(printer: BluetoothPrinterUiModel) {
        tryTransition { oldState ->
            this.printer.connect(printer.model)
            oldState
        }
    }
}