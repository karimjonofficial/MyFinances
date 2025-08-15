package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.lib.ui.VerticalSpacer
import com.orka.myfinances.ui.screens.home.parts.CategoriesList
import com.orka.myfinances.ui.screens.home.parts.HomeScreenCarousel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenContent(
    modifier: Modifier,
    state: HomeScreenState.Success
) {
    val carouselState = rememberCarouselState { 3 }

    Column(modifier = modifier) {
        HomeScreenCarousel(state = carouselState)
        VerticalSpacer(24)
        CategoriesList(items = state.folders)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomeContentPreview() {
    val state = HomeScreenState.Success(folders = emptyList())

    Scaffold { innerPadding ->
        HomeScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = state
        )
    }
}