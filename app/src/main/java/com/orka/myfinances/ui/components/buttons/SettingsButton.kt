package com.orka.myfinances.ui.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    topClipped: Boolean = false,
    bottomClipped: Boolean = false,
    leadingIcon: Painter,
    trailingIcon: Painter? = null,
    title: String,
    value: String? = null,
    enabled: Boolean = true,
    error: Boolean,
    onClick: () -> Unit
) {
    val valueColor = if(enabled) {
        if(!error) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    } else {
        ButtonDefaults.filledTonalButtonColors().disabledContentColor
    }

    val shape = when {
        topClipped && bottomClipped -> RoundedCornerShape(50)
        topClipped -> RoundedCornerShape(topStartPercent = 50, topEndPercent = 50)
        bottomClipped -> RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50)
        else -> RoundedCornerShape(0.dp)
    }

    FilledTonalButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 24.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp),
            painter = leadingIcon,
            contentDescription = null,
            tint = valueColor
        )

        HorizontalSpacer(16)
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )

                    if (value != null) {
                        HorizontalSpacer(16)
                        Text(
                            modifier = Modifier.weight(1f),
                            text = value,
                            color = valueColor,
                            maxLines = 1,
                            textAlign = TextAlign.End
                        )
                    }
                }

                if (trailingIcon != null) {
                    HorizontalSpacer(16)
                    Icon(
                        painter = trailingIcon,
                        contentDescription = null
                    )
                }
            }
        }
    }
}