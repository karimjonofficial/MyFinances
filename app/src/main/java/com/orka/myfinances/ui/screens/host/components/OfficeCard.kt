package com.orka.myfinances.ui.screens.host.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OfficeCard(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onChecked: (OfficeUiModel) -> Unit,
    model: OfficeUiModel
) {
    ListItem(
        modifier = modifier.clickable { onChecked(model) },
        headlineContent = { Text(text = model.name) },
        leadingContent = {
            Checkbox(
                checked = checked,
                onCheckedChange = null
            )
        }
    )
}