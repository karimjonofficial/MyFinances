package com.orka.myfinances.ui.screens.templates

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplatesScreen(
    modifier: Modifier = Modifier,
    state: TemplatesScreenState
) {
    when (state) {
        is TemplatesScreenState.Loading -> LoadingScreen(modifier)
        is TemplatesScreenState.Error -> FailureScreen(modifier)

        is TemplatesScreenState.Success -> {
            Scaffold(
                modifier = modifier,
                topBar = { TopAppBar(title = { Text(text = stringResource(R.string.templates)) }) }
            ) { paddingValues ->

                LazyColumn(modifier = Modifier.scaffoldPadding(paddingValues)) {
                    items(items = state.templates) { template: Template ->
                        Text(text = template.name)
                    }
                }
            }
        }
    }
}