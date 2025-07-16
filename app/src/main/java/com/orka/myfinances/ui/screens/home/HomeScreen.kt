package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.ui.screens.main.FailureScreen
import com.orka.myfinances.ui.screens.main.LoadingScreen
import com.orka.myfinances.models.ProductTemplate

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
        ProductTemplate(1),
        ProductTemplate(2),
        ProductTemplate(3),
        ProductTemplate(4),
        ProductTemplate(5),
        ProductTemplate(6),
        ProductTemplate(7),
        ProductTemplate(8),
        ProductTemplate(9),
        ProductTemplate(10)
    )
    val state = HomeScreenState.Success(data)

    HomeScreen(
        modifier = Modifier.fillMaxSize(),
        state = state
    )
}