package com.orka.myfinances.ui.screens.templates.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.template.TemplateFieldModel

@Composable
fun TemplateFieldCard(
    modifier: Modifier = Modifier,
    field: TemplateFieldModel,
    type: String,
    onRemove: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onRemove) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = stringResource(R.string.remove)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = field.name,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}