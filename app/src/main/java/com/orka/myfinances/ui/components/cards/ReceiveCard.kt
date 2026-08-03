package com.orka.myfinances.ui.components.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.format.LocalFormatter
import com.orka.myfinances.lib.ui.components.ListItem
import com.orka.myfinances.ui.models.card.ReceiveCardModel

@Composable
fun ReceiveCard(
    modifier: Modifier = Modifier,
    receive: ReceiveCardModel,
    onClick: () -> Unit
) {
    val formatter = LocalFormatter.current
    ListItem(
        modifier = modifier,
        painter = painterResource(R.drawable.inventory_2),
        headlineText = receive.title,
        supportingText = stringResource(R.string.items_f, formatter.formatNumber(receive.size)),
        price = stringResource(R.string.uzs_f, formatter.formatNumber(receive.price)),
        dateTime = formatter.formatDateTime(receive.dateTime),
        onClick = onClick
    )
}
