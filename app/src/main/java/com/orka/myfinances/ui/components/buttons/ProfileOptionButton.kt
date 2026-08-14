package com.orka.myfinances.ui.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer

@Composable
fun ProfileOptionButton(
    modifier: Modifier = Modifier,
    title: String,
    painter: Painter,
    action: () -> Unit
) {
    FilledTonalButton(
        onClick = action,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = RectangleShape,
        contentPadding = PaddingValues(16.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.secondary
        )

        HorizontalSpacer(16)
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
