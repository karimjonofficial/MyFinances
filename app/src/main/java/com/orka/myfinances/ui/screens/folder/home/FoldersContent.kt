package com.orka.myfinances.ui.screens.folder.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentCarousel
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersList
import com.orka.myfinances.ui.screens.folder.home.state.FoldersState

@Composable
fun FoldersContent(
    modifier: Modifier = Modifier,
    state: FoldersState,
    interactor: FoldersContentInteractor
) {
    when (state) {
        is FoldersState.Initial -> LoadingScreen(modifier)
        is FoldersState.Error -> FailureScreen(modifier)
        is FoldersState.Loading -> LoadingScreen(modifier)

        is FoldersState.Success -> {
            val carouselState = rememberCarouselState { 3 }

            Column(modifier = modifier) {
                FoldersContentCarousel(state = carouselState)

                VerticalSpacer(24)
                FoldersList(
                    items = state.folders,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    onFolderSelected = { interactor.select(it) }
                )
            }
        }
    }
}