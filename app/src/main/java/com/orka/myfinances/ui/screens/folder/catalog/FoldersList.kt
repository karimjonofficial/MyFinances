package com.orka.myfinances.ui.screens.folder.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.screens.folder.components.FirstFolderButton
import com.orka.myfinances.ui.screens.folder.components.FolderButton
import com.orka.myfinances.ui.screens.folder.components.LastFolderButton
import com.orka.myfinances.ui.screens.folder.components.NotLastFolderButton
import com.orka.myfinances.ui.screens.folder.components.SecondFolderButton
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

@Composable
fun FoldersList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    items: List<FolderUiModel>,
    onFolderSelected: (FolderUiModel) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        val size = items.size

        item(span = { GridItemSpan(2) }) {
            Column {
                Text(
                    text = stringResource(R.string.categories),
                    fontWeight = FontWeight.Bold
                )
                VerticalSpacer(8)
            }
        }

        if (size > 0) {
            item {
                FirstFolderButton(
                    folder = items[0].model,
                    onClick = { onFolderSelected(items[0]) }
                )
            }

            if (size > 1) {
                item {
                    SecondFolderButton(
                        folder = items[1].model,
                        onClick = { onFolderSelected(items[1]) }
                    )
                }
            }

            if (size > 4) {
                items(items = items.subList(2, size - (2 - size % 2))) { item ->
                    FolderButton(
                        folder = item.model,
                        onClick = { onFolderSelected(item) }
                    )
                }
            }

            if (size > 2) {
                if (size % 2 == 0) {
                    item {
                        NotLastFolderButton(
                            folder = items[size - 2].model,
                            onClick = { onFolderSelected(items[size - 2]) }
                        )
                    }

                    item {
                        LastFolderButton(
                            folder = items[size - 1].model,
                            onClick = { onFolderSelected(items[size - 1]) }
                        )
                    }

                    item {
                        VerticalSpacer(4)
                    }
                } else {
                    item {
                        NotLastFolderButton(
                            folder = items[size - 1].model,
                            onClick = { onFolderSelected(items[size - 1]) }
                        )
                    }
                }
            }
        } else {
            item {
                Text(text = stringResource(R.string.no_folders_found))
            }
        }
    }
}