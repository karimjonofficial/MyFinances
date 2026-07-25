package com.orka.myfinances.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.data.models.SelectionItemModel
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer

@Composable
fun <T : SelectionItemModel> SelectionItem(
    modifier: Modifier = Modifier,
    model: T,
    selected: Boolean,
    onClick: (T, selected: Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
            .clickable { onClick(model, selected) }
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val leadingIconRes = model.leadingIconRes
        val description = model.description
        val contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

        if (leadingIconRes != null) {
            Icon(
                painter = painterResource(id = leadingIconRes),
                contentDescription = null,
                tint = contentColor
            )
        }

        HorizontalSpacer(8)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = model.title,
                color = contentColor
            )

            if (description != null) {
                Text(
                    text = description,
                    color = contentColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        HorizontalSpacer(8)
        if (selected) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = null,
                tint = contentColor
            )
        }
    }
}