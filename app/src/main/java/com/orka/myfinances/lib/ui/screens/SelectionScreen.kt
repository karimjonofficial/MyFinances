package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.models.SelectionItemModel
import com.orka.myfinances.lib.ui.components.spacer.LazyFooterSpacer
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.components.TopAppBar
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.ui.components.SelectionItem
import com.orka.myfinances.lib.ui.models.ChunkUiModel

@Composable
fun <T : SelectionItemModel> SelectionScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable (State<Map<String, List<T>>>) -> Unit = {},
    selectedContent: LazyListScope.() -> Unit = {},
    state: State<Map<String, List<T>>>,
    isSelected: (T) -> Boolean,
    onSelect: (T, selected: Boolean) -> Unit,
    retry: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = { bottomBar(state) }
    ) { paddingValues ->
        val modifier = Modifier
            .scaffoldPadding(paddingValues)
            .background(MaterialTheme.colorScheme.surfaceContainer)

        LazyColumnWithStickyHeaderContent(
            modifier = modifier,
            arrangementSpace = 2.dp,
            contentPadding = PaddingValues(horizontal = 4.dp),
            state = state,
            refresh = retry,
            header = selectedContent,
            item = {
                val selected = isSelected(it)

                SelectionItem(
                    model = it,
                    selected = selected,
                    onClick = { item, selected ->
                        onSelect(item, selected)
                    }
                )
            },
            footer = LazyListScope::LazyFooterSpacer
        )
    }
}

@Composable
fun <T : SelectionItemModel> PaginatedSelectionScreen(
    modifier: Modifier = Modifier,
    title: String,
    bottomBar: @Composable (State<ChunkUiModel<T>>) -> Unit = {},
    state: State<ChunkUiModel<T>>,
    isSelected: (T) -> Boolean,
    onSelect: (T, selected: Boolean) -> Unit,
    loadMore: () -> Unit,
    refresh: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = title) },
        bottomBar = { bottomBar(state) }
    ) { paddingValues ->
        val modifier = Modifier
            .scaffoldPadding(paddingValues)
            .background(MaterialTheme.colorScheme.surfaceContainer)

        LazyColumnWithStickyHeaderContent(
            modifier = modifier,
            arrangementSpace = 2.dp,
            contentPadding = PaddingValues(horizontal = 4.dp),
            state = state,
            loadMore = loadMore,
            refresh = refresh,
            item = {
                val selected = isSelected(it)

                SelectionItem(
                    model = it,
                    selected = selected,
                    onClick = { item, selected ->
                        onSelect(item, selected)
                    }
                )
            }
        )
    }
}

@Composable
fun <T : SelectionItemModel> SelectionScreen(
    modifier: Modifier = Modifier,
    title: String,
    bottomBar: @Composable (State<Map<String, List<T>>>) -> Unit = {},
    selectedContent: LazyListScope.() -> Unit = {},
    state: State<Map<String, List<T>>>,
    isSelected: (T) -> Boolean,
    onSelect: (T, selected: Boolean) -> Unit,
    retry: () -> Unit
) {
    SelectionScreen(
        modifier = modifier,
        topBar = { TopAppBar(title = title) },
        bottomBar = bottomBar,
        selectedContent = selectedContent,
        state = state,
        isSelected = isSelected,
        onSelect = onSelect,
        retry = retry
    )
}