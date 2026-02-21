package com.orka.myfinances.ui.screens.debt.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun EmphasizedDateCard(
    modifier: Modifier = Modifier,
    label: String,
    date: String,
    painter: Painter,
    contentDescription: String
) {
    DateCard(
        modifier = modifier,
        label = label,
        date = date,
        painter = painter,
        contentDescription = contentDescription,
        containerColor = MaterialTheme.colorScheme.errorContainer,
        textColor = MaterialTheme.colorScheme.onErrorContainer,
        labelColor = MaterialTheme.colorScheme.error,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
    )
}

@Composable
fun DateCard(
    modifier: Modifier = Modifier,
    label: String,
    date: String,
    painter: Painter,
    contentDescription: String
) {
    DateCard(
        modifier = modifier,
        label = label,
        date = date,
        painter = painter,
        contentDescription = contentDescription,
        containerColor = MaterialTheme.colorScheme.surface,
        textColor = MaterialTheme.colorScheme.onSurface,
        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        border = null
    )
}

@Composable
private fun DateCard(
    modifier: Modifier = Modifier,
    label: String,
    date: String,
    painter: Painter,
    contentDescription: String,
    containerColor: Color,
    textColor: Color,
    labelColor: Color,
    border: BorderStroke?
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = border
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {

                Icon(
                    painter = painter,
                    contentDescription = contentDescription
                )

                Spacer(Modifier.size(8.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = labelColor
                )
            }

            Text(
                text = date,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}