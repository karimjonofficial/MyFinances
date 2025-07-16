package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.lib.FailureScreen
import com.orka.myfinances.lib.LoadingScreen
import com.orka.myfinances.models.Category

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

@Preview(
    showSystemUi = false,
    showBackground = true
)
@Composable
private fun HomeScreenPreview() {
    val data = listOf(
        Category(1),
        Category(2),
        Category(3),
        Category(4),
        Category(5),
        Category(6),
        Category(7),
        Category(8),
        Category(9),
        Category(10)
    )
    val state = HomeScreenState.Success(data)

    HomeScreen(
        modifier = Modifier.fillMaxSize(),
        state = state
    )
}