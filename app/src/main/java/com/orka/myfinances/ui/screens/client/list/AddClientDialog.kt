package com.orka.myfinances.ui.screens.client.list

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.valueOrNull
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun AddClientDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    onSuccess: (String, String?, String?, String?, String?) -> Unit
) {
    val firstName = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
    val patronymic = rememberSaveable { mutableStateOf("") }
    val phone = rememberSaveable { mutableStateOf("") }
    val address = rememberSaveable { mutableStateOf("") }

    Dialog(
        modifier = modifier,
        dismissRequest = dismissRequest,
        title = stringResource(R.string.add_a_new_client),
        supportingText = stringResource(R.string.fill_the_lines_below_to_add_a_new_client),
        onSuccess = {
            val nameVal = firstName.value.valueOrNull()
            val lastNameVal = lastName.value.valueOrNull()
            val phoneVal = phone.value.valueOrNull()
            val addressVal = address.value.valueOrNull()
            val patronymicVal = patronymic.value.valueOrNull()

            if (nameVal != null)
                onSuccess(nameVal, lastNameVal, patronymicVal, phoneVal, addressVal)
        }
    ) {
        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text(text = stringResource(R.string.name)) }
        )

        VerticalSpacer(4)
        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text(text = stringResource(R.string.lastname) + "*") }
        )

        VerticalSpacer(4)
        OutlinedTextField(
            value = patronymic.value,
            onValueChange = { patronymic.value = it },
            label = { Text(text = stringResource(R.string.patronymic) + "*") }
        )

        VerticalSpacer(4)
        OutlinedTextField(
            value = phone.value,
            onValueChange = { phone.value = it },
            label = { Text(text = stringResource(R.string.phone_number) + "*") }
        )

        VerticalSpacer(4)
        OutlinedTextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text(text = stringResource(R.string.address) + "*") }
        )
    }
}