package com.orka.myfinances.lib.extensions.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.lib.ui.models.UiText

@Composable
fun UiText.str(): String {
    return when(this) {
        is UiText.Str -> this.value
        is UiText.Res -> stringResource(this.id)
    }
}