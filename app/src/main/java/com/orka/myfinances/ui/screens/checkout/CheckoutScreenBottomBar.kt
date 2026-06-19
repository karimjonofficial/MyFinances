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

@Composable
fun CheckoutScreenBottomBar(
    selectedClient: ClientItemModel?,
    price: Int?,
    onOrderClick: () -> Unit,
    onDebtClick: () -> Unit,
    onSellClick: () -> Unit
) {
    BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
        val filled = selectedClient != null && price != null

        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            enabled = filled,
            onClick = onOrderClick
        ) {
            Text(text = stringResource(R.string.order))
        }

        HorizontalSpacer(8)
        OutlinedButton(
            enabled = filled,
            onClick = onDebtClick
        ) {
            Text(text = stringResource(R.string.debts))
        }

        HorizontalSpacer(8)
        Button(
            enabled = filled,
            onClick = onSellClick
        ) {
            Text(text = stringResource(R.string.sell))
        }
    }
}
