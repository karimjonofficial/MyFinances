package com.orka.myfinances.ui.screens.checkout

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R

@Composable
fun CheckoutScreenTopBar(
    modifier: Modifier = Modifier,
    exposed: Boolean,
    onExposedChange: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(R.string.checkout)) },
        actions = {
            IconButton(onClick = onExposedChange) {
                Icon(
                    painter = painterResource(if (exposed) R.drawable.visibility_off else R.drawable.visibility),
                    contentDescription = null
                )
            }
        }
    )
}