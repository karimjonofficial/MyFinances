package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R

@Composable
fun RangeField(
    modifier: Modifier = Modifier,
    title: String,
    min: Int?,
    max: Int?,
    onMinValueChange: (Int?) -> Unit,
    onMaxValueChange: (Int?) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedIntegerTextField(
            modifier = Modifier.weight(1f),
            value = min,
            onValueChange = { onMinValueChange(it) },
            label = {
                Text(
                    text = "$title(${stringResource(R.string.min)})",
                    softWrap = false
                )
            }
        )

        HorizontalSpacer(8)
        OutlinedIntegerTextField(
            modifier = Modifier.weight(1f),
            value = max,
            onValueChange = { onMaxValueChange(it) },
            label = {
                Text(
                    text = "$title(${stringResource(R.string.max)})",
                    softWrap = false
                )
            }
        )
    }
}