package com.orka.myfinances.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.ScreenTab
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    tabContent: @Composable (index: Int) -> Unit
) {
    val tabs = listOf(
        ScreenTab(
            index = 0,
            title = stringResource(R.string.sale)
        ),
        ScreenTab(
            index = 1,
            title = stringResource(R.string.receive)
        )
    )

    MultipleTabScreen(
        modifier = modifier,
        title = stringResource(R.string.history),
        tabs = tabs,
        tabContent = tabContent
    )
}