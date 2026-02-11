package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleActionBottomBar(
    modifier: Modifier = Modifier,
    buttonText: String,
    action: () -> Unit
) {
    BottomAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 32.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = action
        ) {
            Text(text = buttonText)
        }
    }
}