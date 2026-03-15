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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.ui.screens.home.models.FolderButtonModel
import com.orka.myfinances.application.viewmodels.home.folder.toModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

private const val roundedCornerRadius = 24
private const val normalCornerRadius = 8

@Composable
fun FolderButton(
    modifier: Modifier = Modifier,
    folder: FolderButtonModel,
    onClick: () -> Unit
) {
    BaseFolderButton(
        modifier = modifier,
        folder = folder,
        onClick = onClick
    )
}



@Composable
private fun BaseFolderButton(
    modifier: Modifier = Modifier,
    folder: FolderButtonModel,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = folder.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            HorizontalSpacer(8)
            
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(folder.iconRes),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = null
                )
            }
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
            FolderButton(folder = category1.toModel()) {}
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
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            FolderButton(folder = category1.toModel()) {}
        }
    }
}