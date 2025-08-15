package com.orka.myfinances.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.lib.ui.HorizontalSpacer

private const val roundedCornerRadius = 16
private const val normalCornerRadius = 4

@Composable
fun CategoryButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseCategoryButton(
        modifier = modifier,
        folder = folder,
        onClick = onClick
    )
}

@Composable
fun FirstCategoryButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseCategoryButton(
        modifier = modifier,
        topStart = roundedCornerRadius.dp,
        folder = folder,
        onClick = onClick
    )
}

@Composable
fun SecondCategoryButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseCategoryButton(
        modifier = modifier,
        topEnd = roundedCornerRadius.dp,
        folder = folder,
        onClick = onClick
    )
}

@Composable
fun NotLastCategoryButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseCategoryButton(
        modifier = modifier,
        bottomStart = roundedCornerRadius.dp,
        folder = folder,
        onClick = onClick
    )
}

@Composable
fun LastCategoryButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseCategoryButton(
        modifier = modifier,
        bottomEnd = roundedCornerRadius.dp,
        folder = folder,
        onClick = onClick
    )
}

@Composable
private fun BaseCategoryButton(
    modifier: Modifier = Modifier,
    topStart: Dp = normalCornerRadius.dp,
    topEnd: Dp = normalCornerRadius.dp,
    bottomStart: Dp = normalCornerRadius.dp,
    bottomEnd: Dp = normalCornerRadius.dp,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = topStart,
                    topEnd = topEnd,
                    bottomEnd = bottomEnd,
                    bottomStart = bottomStart
                )
            )
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = folder.name,
                color = MaterialTheme.colorScheme.onSurface
            )

            HorizontalSpacer(8)
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.star_filled),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        }
    }
}