package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R

@Composable
fun SingleActionBottomBar(
    modifier: Modifier = Modifier,
    buttonText: String = stringResource(R.string.save),
    buttonEnabled: Boolean = true,
    action: () -> Unit
) {
    BottomAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 32.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonEnabled,
            onClick = action
        ) {
            Text(text = buttonText)
        }
    }
}