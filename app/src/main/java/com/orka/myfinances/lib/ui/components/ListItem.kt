package com.orka.myfinances.lib.ui.components

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
fun ListItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    headlineText: String,
    supportingText: String,
    price: String,
    dateTime: String? = null,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier.clickable { onClick() },
        leadingContent = {
            Icon(
                painter = painter,
                contentDescription = null
            )
        },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = price)
                if (dateTime != null) Text(text = dateTime)
            }
        },
        supportingContent = { Text(text = supportingText) },
        content = {
            Text(
                text = headlineText,
                maxLines = 2
            )
        },
    )
}