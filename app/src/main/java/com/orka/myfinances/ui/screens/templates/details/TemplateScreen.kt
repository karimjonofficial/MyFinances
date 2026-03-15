package com.orka.myfinances.ui.screens.templates.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.template.template1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.DescriptionCard
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun TemplateScreen(
    modifier: Modifier = Modifier,
    state: State,
    interactor: TemplateScreenInteractor
) {
    when (state) {
        is State.Initial -> LoadingScreen(
            modifier = modifier,
            action = interactor::initialize
        )

        is State.Loading -> LoadingScreen(modifier)
        is State.Failure -> { /* Handle error */ }

        is State.Success<*> -> {
            val template = state.value as TemplateScreenModel
            Scaffold(
                modifier = modifier,
                title = template.name,
            ) { paddingValues ->

                Column(
                    modifier = Modifier
                        .scaffoldPadding(paddingValues)
                        .padding(horizontal = 16.dp)
                ) {
                    DividedList(
                        title = stringResource(R.string.properties_of_template),
                        items = template.fields,
                        itemTitle = { it.name },
                        itemSupportingText = { it.type }
                    )

                    VerticalSpacer(8)
                    if (!template.description.isNullOrBlank()) {
                        DescriptionCard(description = template.description)
                        VerticalSpacer(8)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TemplateScreenPreview() {
    MyFinancesTheme {
        TemplateScreen(
            modifier = Modifier.fillMaxSize(),
            state = State.Success(template1.map()),
            interactor = TemplateScreenInteractor.dummy
        )
    }
}