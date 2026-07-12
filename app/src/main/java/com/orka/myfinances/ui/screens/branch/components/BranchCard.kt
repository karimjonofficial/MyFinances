package com.orka.myfinances.ui.screens.branch.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BranchCard(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onChecked: (BranchUiModel) -> Unit,
    model: BranchUiModel
) {
    ListItem(
        modifier = modifier.clickable { onChecked(model) },
        leadingContent = {
                Checkbox(
                    checked = checked,
                    onCheckedChange = null
                )
            },
        trailingContent = null,
        overlineContent = null,
        supportingContent = null,
        colors = ListItemDefaults.colors(),
        elevation = ListItemDefaults.elevation(),
        content = { Text(text = model.name) },
    )
}