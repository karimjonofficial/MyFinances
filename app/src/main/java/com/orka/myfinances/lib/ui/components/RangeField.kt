package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R

@Composable
fun RangeField(
    modifier: Modifier = Modifier,
    min: Int,
    max: Int,
    onMinValueChange: (Int) -> Unit,
    onMaxValueChange: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = min.toString(),
            onValueChange = {
                val minValue = it.toIntOrNull()
                if (minValue != null) {
                    onMinValueChange(minValue)
                }
            },
            label = { Text(text = stringResource(R.string.min)) }
        )

        HorizontalSpacer(8)
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = max.toString(),
            onValueChange = {
                val maxValue = it.toIntOrNull()
                if (maxValue != null) {
                    onMaxValueChange(maxValue)
                }
            },
            label = { Text(text = stringResource(R.string.max)) }
        )
    }
}