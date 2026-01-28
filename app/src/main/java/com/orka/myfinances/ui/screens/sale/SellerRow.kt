package com.orka.myfinances.ui.screens.sale

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.orka.myfinances.lib.ui.components.HorizontalSpacer

@Composable
fun SellerRow(
    modifier: Modifier = Modifier,
    name: String,
    role: String,
    imageUrl: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(size = 48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        HorizontalSpacer(16)
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = role,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}