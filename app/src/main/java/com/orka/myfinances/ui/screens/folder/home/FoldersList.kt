package com.orka.myfinances.ui.screens.folder.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
    contentPadding: PaddingValues = PaddingValues(0.dp),
    items: List<FolderUiModel>,
    onFolderSelected: (FolderUiModel) -> Unit
) {
    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = stringResource(R.string.categories),
            fontWeight = FontWeight.Bold
        )
        VerticalSpacer(4)

        val size = items.size
        if (size > 0) {
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                FirstFolderButton(
                    modifier = Modifier.weight(1f),
                    folder = items[0].model,
                    onClick = { onFolderSelected(items[0]) }
                )
                if (size > 1) {
                    SecondFolderButton(
                        modifier = Modifier.weight(1f),
                        folder = items[1].model,
                        onClick = { onFolderSelected(items[1]) }
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            if (size > 2) {
                val remaining = if (size % 2 == 0) items.subList(2, size - 2) else items.subList(2, size - 1)

                remaining.chunked(2).forEach { rowItems ->
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        rowItems.forEach { item ->
                            FolderButton(
                                modifier = Modifier.weight(1f),
                                folder = item.model,
                                onClick = { onFolderSelected(item) }
                            )
                        }
                    }
                }

                if (size % 2 == 0) {
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        NotLastFolderButton(
                            modifier = Modifier.weight(1f),
                            folder = items[size - 2].model,
                            onClick = { onFolderSelected(items[size - 2]) }
                        )
                        LastFolderButton(
                            modifier = Modifier.weight(1f),
                            folder = items[size - 1].model,
                            onClick = { onFolderSelected(items[size - 1]) }
                        )
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        NotLastFolderButton(
                            modifier = Modifier.weight(1f).padding(bottom = 4.dp),
                            folder = items[size - 1].model,
                            onClick = { onFolderSelected(items[size - 1]) }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_folders_found),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
