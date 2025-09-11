package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.FolderType
import com.orka.myfinances.lib.ui.Dialog
import com.orka.myfinances.lib.ui.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.RadioButton

val FolderTypeSaver = Saver<FolderType, Pair<String, Int?>>(
    save = {
        when (it) {
            is FolderType.Catalog -> "catalog" to null
            is FolderType.ProductFolder -> "product" to it.templateId
        }
    },
    restore = {
        when (it.first) {
            "catalog" -> FolderType.Catalog
            "product" -> FolderType.ProductFolder(it.second!!)
            else -> error("Unknown FolderType")
        }
    }
)

@Composable
fun AddFolderDialog(
    templates: List<Template>,
    dismissRequest: () -> Unit,
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

        Column {
            Text(text = stringResource(R.string.select_folder_type))

            RadioButton(
                text = stringResource(R.string.catalog),
                selected = folderType.intValue == 0,
                onClick = {
                    folderType.intValue = 0
                    folder.value = FolderType.Catalog
                    templatesVisible.value = false
                }
            )

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
                        folder.value = FolderType.ProductFolder(it.id.value)
                    }
                )
            }
        }
    }
}