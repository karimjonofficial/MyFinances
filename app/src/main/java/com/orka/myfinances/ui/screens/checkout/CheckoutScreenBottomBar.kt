package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.ui.models.item.ClientItemModel

@Composable
fun CheckoutScreenBottomBar(
    transactionType: TransactionType,
    paymentType: PaymentType,
    selectedClient: ClientItemModel?,
    onExecute: () -> Unit
) {
    BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
        val clientRequired = paymentType == PaymentType.DEBT || transactionType == TransactionType.ORDER
        val enabled = if (clientRequired) selectedClient != null else true

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            onClick = onExecute
        ) {
            Text(
                text = when {
                    transactionType == TransactionType.ORDER -> stringResource(R.string.create_order)
                    paymentType == PaymentType.DEBT -> stringResource(R.string.sell_on_debt)
                    else -> stringResource(R.string.sell)
                }
            )
        }
    }
}
