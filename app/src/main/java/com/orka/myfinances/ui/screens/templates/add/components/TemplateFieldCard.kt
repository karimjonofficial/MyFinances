package com.orka.myfinances.ui.screens.templates.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.HorizontalSpacer

@Composable
fun TemplateFieldCard(
    modifier: Modifier = Modifier,
    index: Int,
    name: String,
    type: String,
    types: List<String>,
    onNameChange: (String) -> Unit,
    onTypeIndexChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    val exposed = rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small
            )
            .padding(start = 12.dp, top = 4.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$index.")

        HorizontalSpacer(4)
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = name,
            onValueChange = { onNameChange(it) },
            label = { Text(text = stringResource(R.string.name)) }
        )

        HorizontalSpacer(4)
        ExposedDropDownTextField(
            modifier = Modifier.width(120.dp),
            menuExpanded = exposed.value,
            onExpandChange = { exposed.value = it },
            onDismissRequested = { exposed.value = false },
            text = type,
            label = stringResource(R.string.type),
            items = types,
            itemText = { it },
            onItemSelected = { index, _ -> onTypeIndexChange(index) }
        )

        HorizontalSpacer(4)
        IconButton(onClick = { onRemove() }) {
            Icon(
                painter = painterResource(R.drawable.remove),
                contentDescription = stringResource(R.string.remove)
            )
        }
    }
}