package com.orka.myfinances.lib.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun FailureScreen(
    modifier: Modifier,
    message: String = stringResource(R.string.failure),
    retry: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        VerticalSpacer(8)
        Button(onClick = retry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}