package com.orka.myfinances.ui.screens.order

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.BlankScreen

@Composable
fun OrderScreen(modifier: Modifier) {//TODO
    BlankScreen(
        modifier = modifier,
        title = stringResource(R.string.orders)
    )
}