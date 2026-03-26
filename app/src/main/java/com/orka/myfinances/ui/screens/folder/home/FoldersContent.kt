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
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentCarousel
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersList
import com.orka.myfinances.ui.screens.folder.home.state.FoldersContentModel

@Composable
fun FoldersContent(
    modifier: Modifier = Modifier,
    state: State<FoldersContentModel>,
    interactor: FoldersContentInteractor
) {
    when (state) {
        is State.Failure -> FailureScreen(modifier)
        is State.Loading -> LoadingScreen(modifier)

        is State.Success -> {
            val carouselState = rememberCarouselState { 3 }

            Column(modifier = modifier) {
                FoldersContentCarousel(state = carouselState)

                VerticalSpacer(24)
                FoldersList(
                    items = state.value.folders,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    onFolderSelected = { interactor.select(it) }
                )
            }
        }
    }
}