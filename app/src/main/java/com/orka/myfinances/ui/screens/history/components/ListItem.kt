package com.orka.myfinances.ui.screens.history.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun <T> ListItem(
    modifier: Modifier = Modifier,
    model: T,
    painter: Painter,
    headlineText: String,
    supportingText: String,
    price: String,
    dateTime: String? = null,
    onClick: (T) -> Unit
) {
    ListItem(
        modifier = modifier.clickable { onClick(model) },
        leadingContent = {
            Icon(
                painter = painter,
                contentDescription = null
            )
        },
        headlineContent = { Text(text = headlineText, softWrap = false) },
        supportingContent = { Text(text = supportingText) },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = price)
                if(dateTime != null) Text(text = dateTime)
            }
        }
    )
}