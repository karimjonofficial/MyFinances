package com.orka.myfinances.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.ui.screens.main.FailureScreen
import com.orka.myfinances.ui.screens.main.LoadingScreen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState
) {
    when (state) {
        HomeScreenState.Initial -> {
            LoadingScreen(modifier)
        }

        is HomeScreenState.Error -> {
            FailureScreen(modifier, state.message) {}
        }

        HomeScreenState.Loading -> {
            LoadingScreen(modifier)
        }

        is HomeScreenState.Success -> {
            HomeScreenContent(modifier, state)
        }
    }


}