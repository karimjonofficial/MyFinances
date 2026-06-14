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
import com.orka.myfinances.ui.models.ClientItemModel
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor

@Composable
fun CheckoutScreenBottomBar(
    selectedClient: ClientItemModel?,
    price: Int?,
    description: String?,
    printReceipt: Boolean,
    interactor: CheckoutScreenInteractor,
) {
    BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
        val filled = selectedClient != null && price != null

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
                }
            }
        ) {
            Text(text = stringResource(R.string.sell))
        }
    }
}
