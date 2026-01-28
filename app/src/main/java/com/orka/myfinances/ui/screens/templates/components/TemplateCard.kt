package com.orka.myfinances.ui.screens.templates.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.template.Template

@Composable
fun TemplateCard(
    modifier: Modifier = Modifier,
    template: Template,
    onClick: (Template) -> Unit
) {
    ListItem(
        modifier = modifier.clickable { onClick(template) },
        headlineContent = {
            Text(text = template.name)
        },
        supportingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    painter = painterResource(R.drawable.docs),
                    contentDescription = stringResource(R.string.template)
                )

                Text(text = "${template.fields.size} fields")
            }
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = template.name
            )
        }
    )
}