package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun <T> DividedList(
    modifier: Modifier = Modifier,
    items: List<T>,
    title: (T) -> String,
    description: (T) -> String
) {
    Card(modifier = modifier) {
        items.forEachIndexed { index, item ->
            ListItem(
                title = title(item),
                description = description(item)
            )
            if (index != items.lastIndex)
                HorizontalDivider()
        }
    }
}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = description,
            fontWeight = FontWeight.SemiBold
        )
    }
}