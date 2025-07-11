package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.FailureScreen
import com.orka.myfinances.lib.LoadingScreen
import com.orka.myfinances.models.Category

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState
) {
    when(state) {
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
            LazyColumn(modifier = modifier) {

                item {
                    val carouselState = rememberCarouselState { 3 }

                    HorizontalMultiBrowseCarousel(
                        state = carouselState,
                        preferredItemWidth = 240.dp,
                        itemSpacing = 8.dp,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) { index ->
                        Image(
                            painter = painterResource(R.drawable.furniture),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = null
                        )
                    }
                }

                items(items = state.data) {
                    Text(text = it.id.toString())
                }
            }
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