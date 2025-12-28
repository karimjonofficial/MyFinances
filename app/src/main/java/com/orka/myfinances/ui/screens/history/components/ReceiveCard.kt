package com.orka.myfinances.ui.screens.history.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.receive.Receive

@Composable
fun ReceiveCard(
    modifier: Modifier = Modifier,
    receive: Receive,
    onClick: (Receive) -> Unit
) {
    ListItem(
        modifier = modifier,
        model = receive,
        painter = painterResource(R.drawable.download),
        headlineText = receive.items[0].product.title.name,
        supportingText = "${receive.items.size} items",
        price = receive.price.toString(),
        dateTime = receive.dateTime.toString(),
        onClick = onClick
    )
}