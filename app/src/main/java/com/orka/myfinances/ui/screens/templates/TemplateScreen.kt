package com.orka.myfinances.ui.screens.templates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.fixtures.resources.models.template.template1
import com.orka.myfinances.lib.extensions.ui.description
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.screens.debt.components.DescriptionCard

@Composable
fun TemplateScreen(
    modifier: Modifier = Modifier,
    template: Template
) {
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
            DescriptionCard(description = template.description.description())
        }
    }
}

@Preview
@Composable
private fun TemplateScreenPreview() {
    TemplateScreen(
        template = template1
    )
}