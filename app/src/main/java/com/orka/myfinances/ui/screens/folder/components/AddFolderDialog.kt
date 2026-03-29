package com.orka.myfinances.ui.screens.folder.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.resources.models.template.template1
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.RadioButton
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.screens.folder.models.TemplateItemModel
import com.orka.myfinances.ui.screens.folder.home.toItemModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddFolderDialog(
    template: TemplateItemModel?,
    dismissRequest: () -> Unit,
    onUnfoldTemplates: () -> Unit,
    onSuccess: (String, String, Id?) -> Unit,
    onCancel: () -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }
    val folderType = rememberSaveable { mutableIntStateOf(0) }

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
            if (nameValue.isNotBlank()) onSuccess(nameValue, folderTypeValue, template?.id)
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
                }
            )

            VerticalSpacer(4)

            RadioButton(
                text = stringResource(R.string.product_folder),
                selected = folderType.intValue == 1,
                onClick = {
                    folderType.intValue = 1
                }
            )

            if (folderType.intValue == 1) {
                VerticalSpacer(8)
                if(template == null) {
                    Button(onClick = onUnfoldTemplates) {
                        Text(text = stringResource(R.string.select_template))
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(text = stringResource(R.string.template))
                        HorizontalSpacer(8)
                        Text(
                            text = template.name,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddFolderDialogPreview1() {
    ScaffoldPreview(title = "Home") {
        AddFolderDialog(
            template = template1.toItemModel(),
            dismissRequest = {},
            onUnfoldTemplates = {},
            onSuccess = { _, _, _ -> },
            onCancel = {}
        )
    }
}

@Preview
@Composable
private fun AddFolderDialogPreview2() {
    ScaffoldPreview(title = "Home") {
        AddFolderDialog(
            template = template1.toItemModel(),
            dismissRequest = {},
            onUnfoldTemplates = {},
            onSuccess = { _, _, _ -> },
            onCancel = {}
        )
    }
}