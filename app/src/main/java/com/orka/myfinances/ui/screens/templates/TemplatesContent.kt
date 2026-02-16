package com.orka.myfinances.ui.screens.templates

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.templates.components.TemplateCard
import com.orka.myfinances.ui.screens.templates.viewmodel.TemplatesScreenState
import com.orka.myfinances.ui.screens.templates.viewmodel.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.templates.viewmodel.toUiModel
import kotlinx.coroutines.flow.flow

@Composable
fun TemplatesContent(
    state: TemplatesScreenState,
    modifier: Modifier,
    viewModel: TemplatesScreenViewModel,
) {
    when (state) {
        is TemplatesScreenState.Loading -> LoadingScreen(modifier)
        is TemplatesScreenState.Error -> FailureScreen(modifier)

        is TemplatesScreenState.Success -> {
            LazyColumn(modifier = modifier) {
                items(items = state.templates) { template ->
                    TemplateCard(
                        template = template.model,
                        onClick = { viewModel.select(template.template) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TemplatesContentPreview() {
    val viewModel = viewModel {
        TemplatesScreenViewModel(
            get = { null },
            events = flow {},
            navigator = DummyNavigator(),
            logger = DummyLogger()
        )
    }
    ScaffoldPreview(title = "Templates") { paddingValues ->
        TemplatesContent(
            state = TemplatesScreenState.Success(templates.map { it.toUiModel() }),
            modifier = Modifier.scaffoldPadding(paddingValues),
            viewModel = viewModel
        )
    }
}