package com.orka.myfinances.ui.screens.templates.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.application.viewmodels.template.list.toUiModel
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen

@Composable
fun TemplatesContent(
    state: TemplatesScreenState,
    modifier: Modifier,
    interactor: TemplatesScreenInteractor,
) {
    when (state) {
        is TemplatesScreenState.Loading -> LoadingScreen(modifier)
        is TemplatesScreenState.Error -> FailureScreen(modifier)

        is TemplatesScreenState.Success -> {
            LazyColumn(modifier = modifier) {
                items(items = state.templates) { template ->
                    TemplateCard(
                        template = template.model,
                        onClick = { interactor.select(template.template) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TemplatesContentPreview() {
    ScaffoldPreview(title = "Templates") { paddingValues ->
        TemplatesContent(
            state = TemplatesScreenState.Success(templates.map { it.toUiModel() }),
            modifier = Modifier.scaffoldPadding(paddingValues),
            interactor = TemplatesScreenInteractor.dummy
        )
    }
}