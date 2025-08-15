package com.orka.myfinances.ui.screens.home.parts

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
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.lib.ui.VerticalSpacer
import com.orka.myfinances.ui.screens.home.components.CategoryButton
import com.orka.myfinances.ui.screens.home.components.FirstCategoryButton
import com.orka.myfinances.ui.screens.home.components.LastCategoryButton
import com.orka.myfinances.ui.screens.home.components.NotLastCategoryButton
import com.orka.myfinances.ui.screens.home.components.SecondCategoryButton

@Composable
fun FoldersList(
    modifier: Modifier = Modifier,
    items: List<Folder>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        val size = items.size

        item(
            span = { GridItemSpan(2) }
        ) {
            Column {
                Text(
                    text = stringResource(R.string.categories),
                    fontWeight = FontWeight.Bold
                )
                VerticalSpacer(8)
            }
        }

        if(size > 0) {
            item { FirstCategoryButton(folder = items[0]) {} }
            if (size > 1) item { SecondCategoryButton(folder = items[1]) {} }
            if (size > 4) items(
                items = items.subList(2, size - 3)
            ) { item -> CategoryButton(folder = item) {} }
            if (size > 2) item {
                if(size == 3) NotLastCategoryButton(folder = items[size - 1]) {}
                else NotLastCategoryButton(folder = items[size - 2]) {}
            }
            if (size > 3) item { LastCategoryButton(folder = items[size - 1]) {} }
        } else {
            item {
                Text(text = stringResource(R.string.no_folders_found))
            }
        }
    }
}