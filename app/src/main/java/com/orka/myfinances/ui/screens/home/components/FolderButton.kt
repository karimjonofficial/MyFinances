package com.orka.myfinances.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.fixtures.resources.models.folder.folder1
import com.orka.myfinances.lib.ui.HorizontalSpacer
import com.orka.myfinances.lib.ui.VerticalSpacer
import com.orka.myfinances.ui.theme.MyFinancesTheme

private const val roundedCornerRadius = 16
private const val normalCornerRadius = 4

@Composable
fun FolderButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseFolderButton(
        modifier = modifier,
        folder = folder,
        onClick = onClick
    )
}

@Composable
fun FirstFolderButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseFolderButton(
        modifier = modifier,
        topStart = roundedCornerRadius.dp,
        folder = folder,
        onClick = onClick
    )
}

@Composable
fun SecondFolderButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseFolderButton(
        modifier = modifier,
        topEnd = roundedCornerRadius.dp,
        folder = folder,
        onClick = onClick
    )
}

@Composable
fun NotLastFolderButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseFolderButton(
        modifier = modifier,
        bottomStart = roundedCornerRadius.dp,
        folder = folder,
        onClick = onClick
    )
}

@Composable
fun LastFolderButton(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    BaseFolderButton(
        modifier = modifier,
        bottomEnd = roundedCornerRadius.dp,
        folder = folder,
        onClick = onClick
    )
}

@Composable
private fun BaseFolderButton(
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
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (folder is Catalog) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = folder.name,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else if (folder is ProductFolder) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = folder.name,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    VerticalSpacer(4)

                    Text(
                        text = "${folder.products.size} ${stringResource(R.string.products)}",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            HorizontalSpacer(8)
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(R.drawable.star_filled),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun CatalogPreview() {
    MyFinancesTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            FolderButton(
                folder = folder1
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ProductFolderPreview() {
    MyFinancesTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            FolderButton(
                folder = folder1
            )
        }
    }
}