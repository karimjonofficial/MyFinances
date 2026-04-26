package com.orka.myfinances.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.Tab
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    tabs: List<Tab>,
    tabContent: @Composable (index: Int) -> Unit
) {
    MultipleTabScreen(
        modifier = modifier,
        title = stringResource(R.string.history),
        tabs = tabs,
        tabContent = tabContent
    )
}