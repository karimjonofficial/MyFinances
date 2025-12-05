package com.orka.myfinances.lib.extensions.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

fun Modifier.scaffoldPadding(paddingValues: PaddingValues): Modifier {
    return this.fillMaxSize().padding(paddingValues)
}