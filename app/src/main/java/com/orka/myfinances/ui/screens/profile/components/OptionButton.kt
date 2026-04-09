package com.orka.myfinances.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R

@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    title: String,
    painter: Painter,
    action: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { action() }
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = title
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null
        )
    }
}