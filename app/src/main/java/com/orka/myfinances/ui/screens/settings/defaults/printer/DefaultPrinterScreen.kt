package com.orka.myfinances.ui.screens.settings.defaults.printer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.screens.SelectionScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.ui.PrinterUiModel

@Composable
fun DefaultPrinterScreen(
    modifier: Modifier = Modifier,
    screenState: State<PrinterUiModel>,
    listState: State<Map<String, List<PrinterUiModel>>>,
    onSave: (PrinterUiModel) -> Unit,
    onRetry: () -> Unit
) {
    val selectedPrinter = retain { mutableStateOf(screenState.value) }

    LaunchedEffect(screenState) {
        if (screenState is State.Success) selectedPrinter.value = screenState.value
    }

    SelectionScreen(
        modifier = modifier,
        title = stringResource(R.string.default_printer),
        bottomBar = {
            SingleActionBottomBar(
                buttonEnabled = screenState !is State.Loading
                        && listState is State.Success
                        && selectedPrinter.value != null
                        && selectedPrinter.value != screenState.value,
                action = {
                    val printer = selectedPrinter.value
                    if (printer != null) onSave(printer)
                }
            )
        },
        state = listState,
        isSelected = { selectedPrinter.value == it },
        onSelect = { it, selected -> if (!selected) selectedPrinter.value = it },
        retry = onRetry
    )
}