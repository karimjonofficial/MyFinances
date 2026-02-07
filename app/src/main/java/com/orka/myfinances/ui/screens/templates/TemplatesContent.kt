package com.orka.myfinances.ui.screens.templates

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.components.TemplateCard
import com.orka.myfinances.ui.screens.templates.viewmodel.TemplatesScreenState

@Composable
fun TemplatesContent(
    state: TemplatesScreenState,
    modifier: Modifier,
    navigator: Navigator
) {
    when (state) {
        is TemplatesScreenState.Loading -> LoadingScreen(modifier)
        is TemplatesScreenState.Error -> FailureScreen(modifier)

        is TemplatesScreenState.Success -> {
            LazyColumn(modifier = modifier) {
                items(items = state.templates) { template ->
                    TemplateCard(
                        template = template,
                        onClick = { navigator.navigateToTemplate(it) }
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
            state = TemplatesScreenState.Success(templates),
            modifier = Modifier.scaffoldPadding(paddingValues),
            navigator = DummyNavigator()
        )
    }
}