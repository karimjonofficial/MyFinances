package com.orka.myfinances.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    tabContent: @Composable (index: Int, searchActive: Boolean) -> Unit
) {
    val searchMode = rememberSaveable { mutableStateOf(false) }
    val searchText = rememberSaveable { mutableStateOf("") }

    MultipleTabScreen(
        modifier = modifier,
        topBar = { selectedIndex ->
            SearchTopAppBar(
                title = stringResource(R.string.history),
                onSearch = { onSearch(selectedIndex, it) },
                searchMode = searchMode.value,
                onSearchModeChange = { searchMode.value = it },
                searchText = searchText.value,
                onSearchTextChange = { searchText.value = it }
            )
        },
        tabs = tabs,
        tabContent = { index ->
            tabContent(index, searchMode.value)
        }
    )
}
