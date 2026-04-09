package com.orka.myfinances.ui.screens.templates.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.template.TemplateFieldModel

@Composable
fun AddTemplateScreenBottomBar(
    modifier: Modifier = Modifier,
    name: String,
    fields: List<TemplateFieldModel>,
    interactor: AddTemplateScreenInteractor
) {
    BottomAppBar(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    if (name.isNotBlank() && fields.isNotEmpty()) {
                        interactor.addTemplate(name, fields)
                    }
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}