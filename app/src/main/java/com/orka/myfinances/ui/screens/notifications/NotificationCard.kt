package com.orka.myfinances.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NotificationCard(
    modifier: Modifier,
    notification: NotificationCardModel,
    onClick: () -> Unit
) {
    val read = notification.read
    val backgroundColor = if (read) {
        MaterialTheme.colorScheme.surfaceContainerLow
    } else {
        MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val titleColor = if (read) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val timestampColor = if (read) {
        MaterialTheme.colorScheme.outline
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val borderColor = if (read) {
        MaterialTheme.colorScheme.outlineVariant
    } else {
        MaterialTheme.colorScheme.outline
    }

    val titleStyle = MaterialTheme.typography.bodyLarge
    val titleFontWeight = if (read) FontWeight.Normal else FontWeight.Bold
    val messageStyle = MaterialTheme.typography.bodyMedium
    val timestampStyle = if (read) {
        MaterialTheme.typography.labelSmall
    } else {
        MaterialTheme.typography.labelMedium
    }

    BadgedBox(
        modifier = modifier
            .clickable { onClick() }
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor),
        badge = { if (!read) Badge() },
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = notification.title,
                        color = titleColor,
                        style = titleStyle,
                        fontWeight = titleFontWeight
                    )
                    Text(
                        text = notification.time,
                        color = timestampColor,
                        style = timestampStyle
                    )
                }
                Text(
                    text = notification.message,
                    color = titleColor,
                    style = messageStyle
                )
            }
        }
    )
}
