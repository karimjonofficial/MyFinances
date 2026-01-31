package com.orka.myfinances.lib.ui.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> ExposedDropDownTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    menuExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onDismissRequested: () -> Unit,
    items: List<T>?,
    itemText: (T) -> String,
    onItemSelected: (T) -> Unit
) {

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = menuExpanded,
        onExpandedChange = { onExpandChange(it) }
    ) {
        TextField(
            value = text,
            onValueChange = {},
            modifier = modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            label = { Text(text = label) },
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = onDismissRequested
        ) {
            items?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemText(item)) },
                    onClick = {
                        onItemSelected(item)
                        onDismissRequested()
                    }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> OutlinedExposedDropDownTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    menuExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onDismissRequested: () -> Unit,
    items: List<T>?,
    itemText: (T) -> String,
    onItemSelected: (T) -> Unit
) {

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = menuExpanded,
        onExpandedChange = { onExpandChange(it) }
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {},
            modifier = modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            label = { Text(text = label) },
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = onDismissRequested
        ) {
            items?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemText(item)) },
                    onClick = {
                        onItemSelected(item)
                        onDismissRequested()
                    }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> OutlinedExposedDropDownTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    menuExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onDismissRequested: () -> Unit,
    items: List<T>?,
    itemText: (T) -> String,
    onItemSelected: (Int, T) -> Unit
) {

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = menuExpanded,
        onExpandedChange = { onExpandChange(it) }
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {},
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            label = { Text(text = label) },
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) }
        )

        ExposedDropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = onDismissRequested
        ) {
            items?.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(itemText(item)) },
                    onClick = {
                        onItemSelected(index, item)
                        onDismissRequested()
                    }
                )
            }
        }
    }
}