package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer

@Composable
fun <T> DividedList(
    modifier: Modifier = Modifier,
    title: String,
    items: List<T>,
    itemTitle: @Composable (T) -> String,
    itemSupportingText: @Composable (T) -> String
) {
    if (items.isEmpty()) return

    Column(
        modifier = modifier
    ) {
        DividedListTitle(
            modifier = Modifier.fillMaxWidth(),
            title = title,
            size = items.size
        )

        VerticalSpacer(8)
        DividedListImpl(
            items = items,
            title = itemTitle,
            supportingText = itemSupportingText
        )
    }
}

@Composable
private fun <T> DividedListImpl(
    modifier: Modifier = Modifier,
    items: List<T>,
    title: @Composable (T) -> String,
    supportingText: @Composable (T) -> String
) {
    Card(modifier = modifier) {
        for (i in items.indices) {
            ListItem(
                title = title(items[i]),
                description = supportingText(items[i])
            )
            if (i < items.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun DividedListTitle(
    modifier: Modifier = Modifier,
    title: String,
    size: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SectionTitle(text = title)

        Surface(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "$size",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(
                    horizontal = 10.dp,
                    vertical = 4.dp
                )
            )
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
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        HorizontalSpacer(8)
        Text(
            modifier = Modifier.weight(3f),
            textAlign = TextAlign.End,
            text = description,
            fontWeight = FontWeight.SemiBold
        )
    }
}
