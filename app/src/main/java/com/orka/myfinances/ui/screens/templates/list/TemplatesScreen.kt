package com.orka.myfinances.ui.screens.templates.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.components.TopAppBar
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun TemplatesScreen(
    modifier: Modifier = Modifier,
    state: State<ChunkMapState<TemplateUiModel>>,
    interactor: TemplatesScreenInteractor
) {
    LazyColumnWithStickyHeaderScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(R.string.templates),
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
        loadMore = interactor::loadMore,
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
                value = ChunkMapState(
                    count = 10,
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