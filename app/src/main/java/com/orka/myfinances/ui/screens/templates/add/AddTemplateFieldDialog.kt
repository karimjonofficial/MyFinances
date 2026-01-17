package com.orka.myfinances.ui.screens.templates.add

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun AddTemplateFieldDialog(
    modifier: Modifier = Modifier,
    types: List<String>,
    dismissRequest: () -> Unit,
    onSuccess: (String, Int) -> Unit
) {
    val newFieldName = rememberSaveable { mutableStateOf("") }
    val newFieldTypeIndex = rememberSaveable { mutableStateOf<Int?>(null) }

    Dialog(
        modifier = modifier,
        dismissRequest = dismissRequest,
        title = stringResource(R.string.add_field),
        supportingText = stringResource(R.string.fill_the_lines_below_to_add_a_new_field),
        onSuccess = {
            val name = newFieldName.value
            val index = newFieldTypeIndex.value

            if (name.isNotBlank() && index != null) {
                onSuccess(name, index)
                dismissRequest()
            }
        },
        content = {
            val exposed = rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                value = newFieldName.value,
                onValueChange = { newFieldName.value = it },
                label = { Text(text = stringResource(R.string.name)) }
            )

            VerticalSpacer(8)
            OutlinedExposedDropDownTextField(
                menuExpanded = exposed.value,
                onExpandChange = { exposed.value = it },
                onDismissRequested = { exposed.value = false },
                text = if (newFieldTypeIndex.value == null) "" else types[newFieldTypeIndex.value!!],
                label = stringResource(R.string.type),
                items = types,
                itemText = { it },
                onItemSelected = { index, _ -> newFieldTypeIndex.value = index }
            )
        }
    )
}