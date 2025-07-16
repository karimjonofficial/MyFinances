package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenContent(
    modifier: Modifier,
    state: HomeScreenState.Success
) {
    val carouselState = rememberCarouselState { 3 }

    LazyColumn(modifier = modifier) {
        item {
            HorizontalMultiBrowseCarousel(
                state = carouselState,
                preferredItemWidth = 240.dp,
                itemSpacing = 8.dp,
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) { index ->
                Image(
                    modifier = Modifier.maskClip(MaterialTheme.shapes.extraLarge),
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