package com.orka.myfinances.ui.screens.folder.home.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.CarouselState
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FoldersContentCarousel(
    modifier: Modifier = Modifier,
    state: CarouselState,
    itemCount: Int
) {
    Column(
        modifier = modifier
    ) {
        HorizontalMultiBrowseCarousel(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            state = state,
            preferredItemWidth = 280.dp,
            itemSpacing = 4.dp
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .maskClip(MaterialTheme.shapes.medium),
                painter = painterResource(R.drawable.furniture),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        VerticalSpacer(8)
        CarouselIndicators(
            modifier = Modifier.fillMaxWidth(),
            itemCount = itemCount,
            currentIndex = state.currentItem
        )
    }
}

@Composable
fun CarouselIndicators(
    modifier: Modifier = Modifier,
    itemCount: Int,
    currentIndex: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(itemCount) { index ->
            val isSelected = currentIndex == index
            val color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outlineVariant
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .size(if (isSelected) 8.dp else 6.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
