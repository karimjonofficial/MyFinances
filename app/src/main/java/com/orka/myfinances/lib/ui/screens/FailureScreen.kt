package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun FailureScreen(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.failure),
    retry: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        if(retry != null) {
            VerticalSpacer(8)
            Button(onClick = { retry.invoke() }) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}