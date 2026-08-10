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
import com.orka.myfinances.lib.ui.components.spacer.LazyFooterSpacer
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.screen.SettingsScreenModel
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
            contentPadding = PaddingValues(horizontal = 6.dp)
        ) {
            val topSpace = 12
            val gapBetweenGroups = 8

            item { VerticalSpacer(topSpace) }
            DefaultsGroup(interactor, state)

            item { VerticalSpacer(gapBetweenGroups) }
            HomeContentGroup(interactor, state)

            item { VerticalSpacer(gapBetweenGroups) }
            PrinterGroup(interactor, state)

            LazyFooterSpacer()
        }
    }
}

@DefaultPreview
@Composable
private fun SettingsScreenPreview() {
    MyFinancesTheme {
        SettingsScreen(
            state = State.Success(
                value = SettingsScreenModel(
                    defaultCategory = category1.name,
                    defaultPrinter = "XPrinter XP-323 B",
                    pairedPrinter = "XPrinter XP-323 B"
                )
            ),
            interactor = SettingsScreenInteractor.dummy
        )
    }
}