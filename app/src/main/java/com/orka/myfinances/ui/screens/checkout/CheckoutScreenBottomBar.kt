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
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel

@Composable
fun CheckoutScreenBottomBar(
    price: Int?,
    selectedClient: ClientItemModel?,
    newClientFirstName: String?,
    newClientLastName: String?,
    newClientPatronymic: String?,
    newClientPhone: String?,
    newClientAddress: String?,
    interactor: CheckoutScreenInteractor,
    description: String?,
    printReceipt: Boolean
) {
    BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
        val filled = (selectedClient != null || newClientFirstName != null) && price != null

        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            enabled = filled,
            onClick = {
                if (selectedClient != null) {
                    interactor.order(
                        price = price,
                        description = description,
                        clientId = selectedClient.id
                    )
                } else {
                    interactor.order(
                        firstName = newClientFirstName!!,
                        lastName = newClientLastName,
                        patronymic = newClientPatronymic,
                        phone = newClientPhone,
                        address = newClientAddress,
                        price = price,
                        description = description
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
                if (selectedClient != null) {
                    interactor.sell(
                        price = price,
                        description = description,
                        clientId = selectedClient.id,
                        print = printReceipt
                    )
                } else {
                    interactor.sell(
                        firstName = newClientFirstName!!,
                        lastName = newClientLastName,
                        patronymic = newClientPatronymic,
                        phone = newClientPhone,
                        address = newClientAddress,
                        price = price,
                        description = description,
                        print = printReceipt
                    )
                }
            }
        ) {
            Text(text = stringResource(R.string.sell))
        }
    }
}
