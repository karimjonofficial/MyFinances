package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.screens.home.components.FolderButton
import com.orka.myfinances.ui.screens.home.models.FolderUiModel

@Composable
fun FoldersList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
    items: List<FolderUiModel>,
    onFolderSelected: (FolderUiModel) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val size = items.size

        item(span = { GridItemSpan(2) }) {
            Column {
                Text(
                    text = stringResource(R.string.categories),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                VerticalSpacer(8)
            }
        }

        if (size > 0) {
            items(items = items) { item ->
                FolderButton(
                    modifier = Modifier.fillMaxSize(),
                    folder = item.model,
                    onClick = { onFolderSelected(item) }
                )
            }
        } else {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = stringResource(R.string.no_folders_found),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}