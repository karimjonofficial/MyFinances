package com.orka.myfinances.ui.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.ui.models.item.DefaultCategoryItemModel

@Composable
fun DefaultCategoryItem(
    model: DefaultCategoryItemModel,
    selected: Boolean,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        trailingContent = {
            RadioButton(
                selected = selected,
                onClick = null
            )
        },
        content = { Text(text = model.title) },
    )
}