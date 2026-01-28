package com.orka.myfinances.ui.screens.templates.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.orka.myfinances.data.models.template.TemplateField

@Composable
fun TemplateFieldCard(
    modifier: Modifier = Modifier,
    field: TemplateField
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = field.name
        )

        Text(
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End,
            text = field.type,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}