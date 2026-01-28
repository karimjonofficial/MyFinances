package com.orka.myfinances.ui.navigation.entries.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.models.template.template1
import com.orka.myfinances.lib.extensions.ui.description
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.debt.components.DescriptionCard

fun templateEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Template
): NavEntry<Destination> = entry(destination) {

    TemplateScreen(
        modifier = modifier,
        template = destination.template
    )
}

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
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(8.dp)
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = stringResource(R.string.fields)
                )

                VerticalSpacer(8)
                template.fields.dropLast(1).forEach { field ->
                    VerticalSpacer(4)
                    TemplateFieldCard(field = field)
                    VerticalSpacer(4)
                    HorizontalDivider()
                    VerticalSpacer(4)
                }

                TemplateFieldCard(field = template.fields.last())
            }

            VerticalSpacer(8)
            DescriptionCard(description = template.description.description())
        }
    }
}

@Composable
fun TemplateFieldCard(
    modifier: Modifier = Modifier,
    field: TemplateField
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = field.name
        )

        Text(
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End,
            text = field.type,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun TemplateScreenPreview() {
    TemplateScreen(
        template = template1
    )
}