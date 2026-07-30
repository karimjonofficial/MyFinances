package com.orka.myfinances.ui.screens.settings.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: State<SettingsScreenModel>,
    interactor: SettingsScreenInteractor
) {
    Scaffold(
        modifier = modifier,
        title = stringResource(R.string.settings)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            item { VerticalSpacer(16) }
            DefaultsGroup(interactor, state)

            item { VerticalSpacer(8) }
            HomeContentGroup(interactor, state)
        }
    }
}

@DefaultPreview
@Composable
private fun SettingsScreenPreview() {
    MyFinancesTheme {
        SettingsScreen(
            state = State.Success(
                value = SettingsScreenModel(category1.name)
            ),
            interactor = SettingsScreenInteractor.dummy
        )
    }
}