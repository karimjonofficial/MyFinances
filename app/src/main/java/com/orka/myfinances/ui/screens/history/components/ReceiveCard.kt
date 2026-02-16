package com.orka.myfinances.ui.screens.history.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.R

@Composable
fun ReceiveCard(
    modifier: Modifier = Modifier,
    receive: ReceiveCardModel,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier,
        painter = painterResource(R.drawable.download),
        headlineText = receive.title,
        supportingText = receive.size,
        price = receive.price,
        dateTime = receive.dateTime,
        onClick = onClick
    )
}