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
    interactor: CheckoutScreenInteractor,
    description: String?,
    printReceipt: Boolean
) {
    BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {

        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            enabled = selectedClient != null && price != null,
            onClick = {
                interactor.order(
                    price = price,
                    description = description,
                    clientId = selectedClient!!.id
                )
            }
        ) {
            Text(text = stringResource(R.string.order))
        }

        HorizontalSpacer(8)
        Button(
            enabled = selectedClient != null && price != null,
            onClick = {
                interactor.sell(
                    price = price,
                    description = description,
                    clientId = selectedClient!!.id,
                    print = printReceipt
                )
            }
        ) {
            Text(text = stringResource(R.string.sell))
        }
    }
}