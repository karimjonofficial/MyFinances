package com.orka.myfinances.ui.screens.settings.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.buttons.SettingsButton
import com.orka.myfinances.ui.models.screen.SettingsScreenModel

fun LazyListScope.PrinterGroup(
    interactor: SettingsScreenInteractor,
    state: State<SettingsScreenModel>
) {
    val printer = state.value?.pairedPrinter
    val paired = printer != null

    item {
        SettingsButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.paired_printer),
            topClipped = true,
            bottomClipped = true,
            leadingIcon = if(paired) painterResource(R.drawable.print_connect) else painterResource(R.drawable.print_disabled),
            trailingIcon = painterResource(R.drawable.arrow_right),
            error = !paired,
            value = if(paired) printer else stringResource(R.string.printer_is_not_paired),
            onClick = interactor::toPrinters,
        )
    }
}