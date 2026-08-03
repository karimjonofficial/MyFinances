package com.orka.myfinances.ui.screens.settings.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.buttons.SettingsButton
import com.orka.myfinances.ui.models.screen.SettingsScreenModel

fun LazyListScope.DefaultsGroup(
    interactor: SettingsScreenInteractor,
    state: State<SettingsScreenModel>
) {
    val loading = state is State.Loading
    val str: @Composable (value: String?, fallbackValue: Int) -> String = { value, fallbackValue -> str(value, fallbackValue, loading) }

    item {
        SettingsButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.default_category),
            topClipped = true,
            bottomClipped = false,
            value = str(state.value?.defaultCategory, R.string.default_category_is_not_set_yet),
            leadingIcon = painterResource(R.drawable.category),
            error = !(loading || state.value?.defaultCategory != null),
            enabled = state is State.Success,
            onClick = interactor::toSelectDefaultCategory,
        )
    }

    item { VerticalSpacer(2) }

    item {
        SettingsButton(
            modifier = Modifier.fillMaxWidth(),
            title = "Default Printer",
            topClipped = false,
            bottomClipped = true,
            value = str(state.value?.defaultPrinter, R.string.default_printer_is_not_set_yet),
            leadingIcon = painterResource(R.drawable.print),
            enabled = false,
            error = !(loading || state.value?.defaultPrinter != null),
            onClick = {}//TODO
        )
    }
}