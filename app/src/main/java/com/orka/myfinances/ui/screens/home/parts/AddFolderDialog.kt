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
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.folder.FolderType
import com.orka.myfinances.lib.extensions.models.FolderTypeSaver
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.RadioButton
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun AddFolderDialog(
    templates: List<Template>,
    dismissRequest: () -> Unit,
    onAddTemplateClick: () -> Unit,
    onSuccess: (String, FolderType) -> Unit,
    onCancel: () -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }
    val templatesVisible = rememberSaveable { mutableStateOf(false) }
    val folderType = rememberSaveable { mutableIntStateOf(0) }
    val folder = rememberSaveable(stateSaver = FolderTypeSaver) { mutableStateOf(FolderType.Catalog) }

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
            val folderTypeValue = folder.value
            if (nameValue.isNotBlank()) onSuccess(nameValue, folderTypeValue)
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
                    folder.value = FolderType.Catalog
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
                val template = rememberSaveable { mutableStateOf<Template?>(null) }
                val dropDownMenuExpanded = rememberSaveable { mutableStateOf(false) }
                val templateValue = template.value

                VerticalSpacer(8)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExposedDropDownTextField(
                        modifier = Modifier.weight(1f),
                        text = templateValue?.name ?: stringResource(R.string.select),
                        label = stringResource(R.string.templates),
                        menuExpanded = dropDownMenuExpanded.value,
                        onExpandChange = { dropDownMenuExpanded.value = it },
                        onDismissRequested = { dropDownMenuExpanded.value = false },
                        items = templates,
                        itemText = { it.name },
                        onItemSelected = {
                            template.value = it
                            folder.value = FolderType.ProductFolder(it.id.value)
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