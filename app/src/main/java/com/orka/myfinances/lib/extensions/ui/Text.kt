package com.orka.myfinances.lib.extensions.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.lib.ui.models.Text

@Composable
fun Text.str(): String {
    return when(this) {
        is Text.Str -> this.value
        is Text.Res -> stringResource(this.id)
    }
}