package com.orka.myfinances.ui.screens.templates

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.lib.ui.FailureScreen
import com.orka.myfinances.lib.ui.LoadingScreen

@Composable
fun TemplatesScreen(
    modifier: Modifier = Modifier,
    state: TemplatesScreenState
) {
    when(state) {
        is TemplatesScreenState.Loading -> {
            LoadingScreen(modifier)
        }

        is TemplatesScreenState.Success -> {
            LazyColumn(modifier = modifier) {
                items(items = state.templates) { template: Template ->
                    Text(text = template.name)
                }
            }
        }

        is TemplatesScreenState.Error -> {
            FailureScreen(modifier) { }
        }
    }
}