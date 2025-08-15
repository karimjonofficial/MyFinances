package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.FolderType
import com.orka.myfinances.lib.ui.Dialog
import com.orka.myfinances.lib.ui.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.RadioButton

@Composable
fun AddFolderDialog(
    templates: List<Template>,
    dismissRequest: () -> Unit,
    onSuccess: (String, FolderType) -> Unit,
    onCancel: () -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }
    val templatesVisible = rememberSaveable { mutableStateOf(false) }
    val folderType = rememberSaveable { mutableStateOf<FolderType>(FolderType.Catalog) }

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
            val folderTypeValue = folderType.value
            if (nameValue.isNotBlank()) onSuccess(nameValue, folderTypeValue)
        }
    ) {
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text(stringResource(R.string.name)) }
        )

        Column {
            Text(text = stringResource(R.string.select_folder_type))

            RadioButton(
                text = stringResource(R.string.catalog),
                selected = folderType.value is FolderType.Catalog,
                onClick = {
                    folderType.value = FolderType.Catalog
                    templatesVisible.value = false
                }
            )

            RadioButton(
                text = stringResource(R.string.product_folder),
                selected = folderType.value is FolderType.ProductFolder,
                onClick = { templatesVisible.value = true }
            )

            if(templatesVisibleValue) {
                val template = rememberSaveable { mutableStateOf<Template?>(null) }
                val dropDownMenuExpanded = rememberSaveable { mutableStateOf(false) }
                val templateValue = template.value

                ExposedDropDownTextField(
                    text = templateValue?.name ?: stringResource(R.string.select),
                    label = stringResource(R.string.templates),
                    menuExpanded = dropDownMenuExpanded.value,
                    onExpandChange = { dropDownMenuExpanded.value = it },
                    onDismissRequested = { dropDownMenuExpanded.value = false },
                    items = templates,
                    itemText = { it.name },
                    onItemSelected = {
                        template.value = it
                        folderType.value = FolderType.ProductFolder(it)
                    }
                )
            }
        }
    }
}