package com.orka.myfinances.ui.screens.clients

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.valueOrNull
import com.orka.myfinances.lib.ui.components.Dialog

@Composable
fun AddClientDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    onSuccess: (String, String?, String?, String?) -> Unit
) {
    val firstName = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
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

            if (nameVal != null)
                onSuccess(nameVal, lastNameVal, phoneVal, addressVal)
        }
    ) {

        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text(text = stringResource(R.string.name)) }
        )

        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text(text = stringResource(R.string.lastname)) }
        )

        TextField(
            value = phone.value,
            onValueChange = { phone.value = it },
            label = { Text(text = stringResource(R.string.phone_number)) }
        )

        TextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text(text = stringResource(R.string.address)) }
        )
    }
}