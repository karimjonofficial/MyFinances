package com.orka.myfinances.lib.ui.components.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalSpacer(width: Int) {
    Spacer(modifier = Modifier.width(width.dp))
}