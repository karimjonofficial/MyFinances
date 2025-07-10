package com.orka.myfinances.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.orka.myfinances.UiState
import com.orka.myfinances.ui.screens.LoginScreen

@Composable
fun MyFinancesContent(
    modifier: Modifier = Modifier,
    state: UiState
) {
    when (state) {
        UiState.Initial -> {
            InitialScreen(modifier = modifier)
        }

        is UiState.Guest -> {
            val uiState = state.viewModel.uiState.collectAsState()

            LoginScreen(
                modifier = modifier,
                uiState = uiState.value,
                viewModel = state.viewModel
            )
        }

        is UiState.SignedIn -> {

            Scaffold(modifier = modifier) { innerPadding ->
                Box(
                    modifier = Modifier.padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Hello World!")

                }
            }
        }
    }
}