package com.orka.myfinances.ui.screens.folder.home.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun FoldersContentCarousel(
    modifier: Modifier = Modifier,
    state: PagerState,
    images: List<Int>,
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.weight(1f)) {
            HorizontalPager(
                modifier = Modifier.fillMaxWidth(),
                state = state
            ) { page ->
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(images[page]),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            CarouselIndicators(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                itemCount = images.size,
                currentIndex = state.currentPage
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    }
}
