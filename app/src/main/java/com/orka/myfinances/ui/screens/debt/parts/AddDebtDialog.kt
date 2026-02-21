package com.orka.myfinances.ui.screens.debt.parts

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.screens.debt.viewmodel.DialogState
import kotlin.time.Instant

@Composable
fun AddDebtDialog(
    state: DialogState,
    dismissRequest: () -> Unit,
    onSuccess: (AddDebtRequest) -> Unit,
    onCancel: () -> Unit
) {
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val description = rememberSaveable { mutableStateOf("") }
    val client = rememberSaveable { mutableStateOf<Client?>(null) }
    val clientDropDownMenuExpanded = rememberSaveable { mutableStateOf(false) }
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
            val clientValue = client.value
            val endDateTimeValue = endDateTime.value
            if (priceValue != null && clientValue != null && endDateTimeValue != null) {
                onSuccess(
                    AddDebtRequest(
                        client = clientValue,
                        price = priceValue,
                        description = description.value,
                        endDateTime = endDateTimeValue
                    )
                )
            }
        }
    ) {
        OutlinedExposedDropDownTextField(
            text = client.value?.firstName ?: stringResource(R.string.client),
            label = stringResource(R.string.client),
            menuExpanded = clientDropDownMenuExpanded.value,
            onExpandChange = { clientDropDownMenuExpanded.value = it },
            onDismissRequested = { clientDropDownMenuExpanded.value = false },
            items = if(state is DialogState.Success) state.clients else emptyList(),
            itemText = { it.firstName },
            onItemSelected = { client.value = it }
        )

        VerticalSpacer(8)
        OutlinedIntegerTextField(
            value = price.value,
            onValueChange = { price.value = it },
            label = stringResource(R.string.price)
        )

        VerticalSpacer(8)
        OutlinedTextField(
            value = endDateTime.value?.toString()?.substring(0, 10) ?: stringResource(R.string.end_date),
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
