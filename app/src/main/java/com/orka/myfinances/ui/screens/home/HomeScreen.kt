package com.orka.myfinances.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.lib.ui.FailureScreen
import com.orka.myfinances.lib.ui.LoadingScreen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onNavigateToFolder: (Folder) -> Unit
) {
    when (state) {
        HomeScreenState.Initial -> {
            LoadingScreen(modifier)
        }

        is HomeScreenState.Error -> {
            FailureScreen(modifier, stringResource(R.string.error)) {}
        }

        HomeScreenState.Loading -> {
            LoadingScreen(modifier)
        }

        is HomeScreenState.Success -> {
            HomeScreenContent(modifier, state, onNavigateToFolder)
        }
    }
}