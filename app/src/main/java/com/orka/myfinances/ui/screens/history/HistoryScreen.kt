package com.orka.myfinances.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.SearchTopAppBar
import com.orka.myfinances.lib.ui.models.Tab
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    tabs: List<Tab>,
    onSearch: (index: Int, query: String) -> Unit,
    tabContent: @Composable (index: Int) -> Unit
) {
    MultipleTabScreen(
        modifier = modifier,
        topBar = { selectedIndex ->
            SearchTopAppBar(
                title = stringResource(R.string.history),
                onSearch = { onSearch(selectedIndex, it) }
            )
        },
        tabs = tabs,
        tabContent = tabContent
    )
}