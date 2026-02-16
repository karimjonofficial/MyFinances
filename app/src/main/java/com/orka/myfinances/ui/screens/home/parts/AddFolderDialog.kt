package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.RadioButton
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.screens.home.viewmodel.folder.TemplateState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddFolderDialog(
    state: TemplateState,
    dismissRequest: () -> Unit,
    onAddTemplateClick: () -> Unit,
    onSuccess: (String, String, Id?) -> Unit,
    showTemplates: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    onCancel: () -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }
    val folderType = rememberSaveable { mutableIntStateOf(0) }
    val templateId = rememberSaveable { mutableStateOf<Id?>(null) }
    val templates = if (state is TemplateState.Success) state.templates else null

    val templatesVisibleValue = showTemplates.value

    Dialog(
        dismissRequest = dismissRequest,
        title = stringResource(R.string.categories),
        supportingText = stringResource(R.string.fill_the_lines_below_to_add_a_new_category),
        cancelTitle = stringResource(R.string.cancel),
        successTitle = stringResource(R.string.add),
        onCancel = onCancel,
        onSuccess = {
            val nameValue = name.value
            val folderTypeValue = if (folderType.intValue == 0) "catalog" else "category"
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
                    showTemplates.value = false
                }
            )

            VerticalSpacer(4)

            RadioButton(
                text = stringResource(R.string.product_folder),
                selected = folderType.intValue == 1,
                onClick = {
                    folderType.intValue = 1
                    showTemplates.value = true
                }
            )

            if (templatesVisibleValue) {
                val dropDownMenuExpanded = rememberSaveable { mutableStateOf(false) }
                val templateValue = templates?.find { it.id == templateId.value }

                VerticalSpacer(8)
                OutlinedExposedDropDownTextField(
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

                VerticalSpacer(8)
                Row {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier.clickable { onAddTemplateClick() },
                        text = stringResource(R.string.add_template),
                        color = ButtonDefaults.textButtonColors().contentColor,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddFolderDialogPreview() {
    ScaffoldPreview(title = "Home") {

        AddFolderDialog(
            state = TemplateState.Success(templates),
            dismissRequest = {},
            onAddTemplateClick = {},
            onSuccess = { _, _, _ -> },
            onCancel = {},
            showTemplates = rememberSaveable { mutableStateOf(true) }
        )
    }
}