package com.orka.myfinances.ui.screens.templates.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.components.SearchTopAppBar
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.cards.TemplateCard
import com.orka.myfinances.ui.models.ui.TemplateUiModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun TemplatesScreen(
    modifier: Modifier = Modifier,
    state: State<ChunkUiModel<TemplateUiModel>>,
    interactor: TemplatesScreenInteractor
) {
    val searchMode = rememberSaveable { mutableStateOf(false) }
    val searchText = rememberSaveable { mutableStateOf("") }

    LazyColumnWithStickyHeaderScreen(
        modifier = modifier,
        topBar = {
            SearchTopAppBar(
                title = stringResource(R.string.templates),
                onSearch = interactor::search,
                searchMode = searchMode.value,
                onSearchModeChange = { searchMode.value = it },
                searchText = searchText.value,
                onSearchTextChange = { searchText.value = it },
                actions = {
                    IconButton(onClick = interactor::addTemplate) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = stringResource(R.string.add)
                        )
                    }
                }
            )
        },
        state = state,
        refresh = interactor::refresh,
        loadMore = {
            if (searchMode.value) interactor.searchMore()
            else interactor.loadMore()
        },
        item = {
            TemplateCard(
                template = it.model,
                onClick = { interactor.select(it) }
            )
        }
    )
}

@Preview
@Composable
private fun TemplatesScreenPreview() {
    MyFinancesTheme {
        TemplatesScreen(
            modifier = Modifier.fillMaxSize(),
            state = State.Success(
                value = ChunkUiModel(
                    size = 10,
                    pageIndex = 1,
                    nextPageIndex = 2,
                    previousPageIndex = null,
                    content = templates
                        .groupBy { it.name.stickyHeaderKey() }
                        .mapValues { it.value.map { template -> template.toUiModel() } }
                )
            ),
            interactor = TemplatesScreenInteractor.dummy
        )
    }
}
