package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.data.models.SelectionItemModel
import com.orka.myfinances.lib.ui.components.FooterSpacer
import com.orka.myfinances.lib.ui.components.LazyColumnWithStickHeader
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.extensions.str
import com.orka.myfinances.lib.viewmodel.State
import com.orka.myfinances.ui.components.items.SelectionItem

@Composable
fun <T: SelectionItemModel> SelectionScreen(
    modifier: Modifier = Modifier,
    title: String,
    bottomBar: @Composable (State<Map<String, List<T>>>) -> Unit = {},
    selectedContent: LazyListScope.() -> Unit = {},
    state: State<Map<String, List<T>>>,
    isSelected: (T) -> Boolean,
    onSelect: (T, selected: Boolean) -> Unit,
    retry: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        title = title,
        bottomBar = { bottomBar(state) }
    ) { paddingValues ->
        val modifier = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is State.Loading -> LoadingScreen(
                modifier = modifier,
                message = state.message.str()
            )

            is State.Failure -> FailureScreen(
                modifier = modifier,
                message = state.error.str(),
                retry = retry
            )

            is State.Success -> {
                LazyColumnWithStickHeader(
                    modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainer),
                    map = state.value,
                    arrangementSpace = 2.dp,
                    contentPadding = PaddingValues(horizontal = 4.dp),
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
                    footer = { FooterSpacer() }
                )
            }
        }
    }
}