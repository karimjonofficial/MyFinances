package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.Dialog

@Composable
fun AddProductDialog(
    dismissRequest: () -> Unit,
    onSuccess: (String) -> Unit,
    onCancel: () -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }

    Dialog(
        dismissRequest = dismissRequest,
        title = stringResource(R.string.categories),
        supportingText = stringResource(R.string.fill_the_lines_below_to_add_a_new_category),
        cancelTitle = stringResource(R.string.cancel),
        successTitle = stringResource(R.string.add),
        onCancel = onCancel,
        onSuccess = {
            val nameValue = name.value
            if (nameValue.isNotBlank()) onSuccess(nameValue)
        }
    ) {
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text(stringResource(R.string.name)) }
        )
    }
}