package com.orka.myfinances.ui.screens.debt.list

import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import kotlin.time.Instant

@Composable
fun AddDebtDialog(
    selectedClient: ClientItemModel?,
    onOpenClients: () -> Unit,
    dismissRequest: () -> Unit,
    onSuccess: (Id, Int, Instant?, String?) -> Unit,
    onCancel: () -> Unit
) {
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val description = rememberSaveable { mutableStateOf("") }
    val endDateTime = rememberSaveable { mutableStateOf<Instant?>(null) }
    val showDatePicker = rememberSaveable { mutableStateOf(false) }

    Dialog(
        dismissRequest = dismissRequest,
        title = stringResource(R.string.add_debt),
        supportingText = stringResource(R.string.fill_the_lines_below_to_add_a_new_debt),
        cancelTitle = stringResource(R.string.cancel),
        successTitle = stringResource(R.string.add),
        onCancel = onCancel,
        onSuccess = {
            val priceValue = price.value
            val clientValue = selectedClient?.id
            val endDateTimeValue = endDateTime.value
            if (priceValue != null && clientValue != null && endDateTimeValue != null) {
                onSuccess(clientValue, priceValue, endDateTimeValue, description.value)
            }
        }
    ) {
        if(selectedClient == null) {
            Button(onClick = onOpenClients) {
                Text(text = stringResource(R.string.select_client))
            }
        } else {
            Text(
                modifier = Modifier.clickable { onOpenClients() },
                text = selectedClient.name,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        VerticalSpacer(8)
        OutlinedIntegerTextField(
            value = price.value,
            onValueChange = { price.value = it },
            label = stringResource(R.string.price)
        )

        VerticalSpacer(8)
        OutlinedTextField(
            value = endDateTime.value?.toString()?.substring(0, 10) ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.end_date)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker.value = true }) {
                    Icon(
                        painter = painterResource(R.drawable.calendar_today),
                        contentDescription = stringResource(R.string.end_date)
                    )
                }
            }
        )

        VerticalSpacer(8)
        OutlinedCommentTextField(
            value = description.value,
            onValueChange = { description.value = it }
        )
    }

    if (showDatePicker.value) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            endDateTime.value = Instant.fromEpochMilliseconds(it)
                        }
                        showDatePicker.value = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker.value = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
