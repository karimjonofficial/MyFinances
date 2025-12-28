package com.orka.myfinances.ui.screens.debt.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.TintIcon

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    contentDescription: String
) {

    TintIcon(
        modifier = modifier,
        painter = painterResource(R.drawable.person),
        contentDescription = contentDescription
    )
}