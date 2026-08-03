package com.orka.myfinances.ui.screens.settings.printers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.screens.SelectionScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.printer.PrinterStatus
import com.orka.myfinances.ui.models.ui.PrinterUiModel

@Composable
fun PrintersScreen(
    modifier: Modifier = Modifier,
    state: State<Map<String, List<PrinterUiModel>>>,
    printerStatus: PrinterStatus,
    interactor: PrintersInteractor
) {
    val printer = retain(keys = arrayOf(printerStatus)) {
        val value = if (printerStatus is PrinterStatus.Connected) {
            val printer = printerStatus.printer

            PrinterUiModel(
                title = printer.name,
                description = printer.address,
                leadingIconRes = R.drawable.bluetooth_connected,
                model = printer
            )
        } else null

        mutableStateOf(value)
    }

    SelectionScreen(
        modifier = modifier,
        title = stringResource(R.string.printers),
        bottomBar = { state ->
            val printer = printer.value

            SingleActionBottomBar(
                buttonText = stringResource(R.string.connect),
                buttonEnabled = state is State.Success && printer != null,
                action = {
                    if (printer != null)
                        interactor.connect(printer)
                }
            )
        },
        state = state,
        isSelected = { printer.value == it },
        onSelect = { it, selected -> if (!selected) printer.value = it },
        retry = interactor::refresh
    )
}