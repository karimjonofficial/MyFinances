package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor

@Composable
fun CheckoutScreenBottomBar(
    uiState: CheckoutUIState,
    interactor: CheckoutScreenInteractor,
) {
    BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
        val filled = (uiState.selectedClient != null || uiState.newClientFirstName != null) && uiState.price != null

        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            enabled = filled,
            onClick = {
                if (uiState.selectedClient != null) {
                    interactor.order(
                        price = uiState.price,
                        description = uiState.description,
                        clientId = uiState.selectedClient!!.id
                    )
                } else {
                    interactor.order(
                        firstName = uiState.newClientFirstName!!,
                        lastName = uiState.newClientLastName,
                        patronymic = uiState.newClientPatronymic,
                        phone = uiState.newClientPhone,
                        address = uiState.newClientAddress,
                        price = uiState.price,
                        description = uiState.description
                    )
                }
            }
        ) {
            Text(text = stringResource(R.string.order))
        }

        HorizontalSpacer(8)
        Button(
            enabled = filled,
            onClick = {
                if (uiState.selectedClient != null) {
                    interactor.sell(
                        price = uiState.price,
                        description = uiState.description,
                        clientId = uiState.selectedClient!!.id,
                        print = uiState.printReceipt
                    )
                } else {
                    interactor.sell(
                        firstName = uiState.newClientFirstName!!,
                        lastName = uiState.newClientLastName,
                        patronymic = uiState.newClientPatronymic,
                        phone = uiState.newClientPhone,
                        address = uiState.newClientAddress,
                        price = uiState.price,
                        description = uiState.description,
                        print = uiState.printReceipt
                    )
                }
            }
        ) {
            Text(text = stringResource(R.string.sell))
        }
    }
}
