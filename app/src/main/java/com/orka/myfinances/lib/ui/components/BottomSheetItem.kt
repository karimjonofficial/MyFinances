package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel

@Composable
fun <T: BottomSheetItemModel> BottomSheetItem(
    modifier: Modifier = Modifier,
    item: T,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() }.padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title,
            maxLines = 1,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )

        if (selected) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}