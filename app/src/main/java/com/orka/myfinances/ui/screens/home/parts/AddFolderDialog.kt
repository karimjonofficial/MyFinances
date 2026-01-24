package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.RadioButton
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.screens.home.viewmodel.TemplateState

@Composable
fun AddFolderDialog(
    state: TemplateState,
    dismissRequest: () -> Unit,
    onAddTemplateClick: () -> Unit,
    onSuccess: (String, String, Id?) -> Unit,
    onCancel: () -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }
    val templatesVisible = rememberSaveable { mutableStateOf(false) }
    val folderType = rememberSaveable { mutableIntStateOf(0) }
    val templateId = rememberSaveable { mutableStateOf<Id?>(null) }
    val templates = if(state is TemplateState.Success) state.templates else null

    val templatesVisibleValue = templatesVisible.value

    Dialog(
        dismissRequest = dismissRequest,
        title = stringResource(R.string.categories),
        supportingText = stringResource(R.string.fill_the_lines_below_to_add_a_new_category),
        cancelTitle = stringResource(R.string.cancel),
        successTitle = stringResource(R.string.add),
        onCancel = onCancel,
        onSuccess = {
            val nameValue = name.value
            val folderTypeValue = if(folderType.intValue == 0) "catalog" else "category"
            if (nameValue.isNotBlank()) onSuccess(nameValue, folderTypeValue, templateId.value)
        }
    ) {
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text(stringResource(R.string.name)) }
        )

        VerticalSpacer(16)

        Column(modifier = Modifier.selectableGroup()) {
            Text(
                text = stringResource(R.string.select_folder_type),
                fontWeight = FontWeight.Bold
            )

            VerticalSpacer(8)
            RadioButton(
                text = stringResource(R.string.catalog),
                selected = folderType.intValue == 0,
                onClick = {
                    folderType.intValue = 0
                    templatesVisible.value = false
                }
            )

            VerticalSpacer(4)

            RadioButton(
                text = stringResource(R.string.product_folder),
                selected = folderType.intValue == 1,
                onClick = {
                    folderType.intValue = 1
                    templatesVisible.value = true
                }
            )

            if(templatesVisibleValue) {
                val dropDownMenuExpanded = rememberSaveable { mutableStateOf(false) }
                val templateValue = templates?.find { it.id == templateId.value }

                VerticalSpacer(8)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedExposedDropDownTextField(
                        modifier = Modifier.weight(1f),
                        text = templateValue?.name ?: stringResource(R.string.select),
                        label = stringResource(R.string.templates),
                        menuExpanded = dropDownMenuExpanded.value,
                        onExpandChange = { dropDownMenuExpanded.value = it },
                        onDismissRequested = { dropDownMenuExpanded.value = false },
                        items = templates,
                        itemText = { it.name },
                        onItemSelected = {
                            templateId.value = it.id
                        }
                    )

                    TextButton(onClick = onAddTemplateClick) {
                        Text(text = stringResource(R.string.add))
                    }
                }
            }
        }
    }
}