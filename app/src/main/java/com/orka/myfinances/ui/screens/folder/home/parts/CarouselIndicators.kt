package com.orka.myfinances.ui.screens.folder.home.parts

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CarouselIndicators(
    modifier: Modifier = Modifier,
    itemCount: Int,
    currentIndex: Int,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(itemCount) { index ->
                val isSelected = currentIndex == index

                val color by animateColorAsState(
                    targetValue = if (isSelected)
                        MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.32f),
                    label = "color"
                )

                val size by animateDpAsState(
                    targetValue = if (isSelected) 8.dp else 6.dp,
                    label = "size"
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(size)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}
