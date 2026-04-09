package com.orka.myfinances.ui.screens.folder.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.FooterSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.contents.StateFulContent
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentCarousel
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

@Composable
fun FoldersContent(
    modifier: Modifier = Modifier,
    state: State<List<FolderUiModel>>,
    interactor: FoldersContentInteractor
) {
    StateFulContent(
        modifier = modifier,
        state = state,
        onRetry = interactor::refresh
    ) { modifier, folder ->
        LazyColumn(modifier = modifier) {
            item {
                val carouselState = rememberCarouselState { 3 }
                FoldersContentCarousel(state = carouselState)
            }

            item {
                VerticalSpacer(24)
                FoldersList(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    items = folder,
                    onFolderSelected = { interactor.select(it) }
                )

                FooterSpacer()
            }
        }
    }
}