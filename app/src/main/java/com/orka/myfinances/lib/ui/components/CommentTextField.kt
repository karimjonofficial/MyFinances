package com.orka.myfinances.lib.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R

@Composable
fun CommentTextField(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
    label: String = stringResource(R.string.comment)
) {
    TextField(
        modifier = modifier,
        value = value ?: "",
        onValueChange = onValueChange,
        label = { Text(text = label) },
        minLines = 3
    )
}