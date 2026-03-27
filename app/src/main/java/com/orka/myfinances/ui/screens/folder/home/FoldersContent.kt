package com.orka.myfinances.ui.screens.folder.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentCarousel
import com.orka.myfinances.ui.screens.folder.home.state.FoldersContentModel

@Composable
fun FoldersContent(
    modifier: Modifier = Modifier,
    state: State<FoldersContentModel>,
    interactor: FoldersContentInteractor
) {
    when (state) {
        is State.Loading -> LoadingScreen(
            modifier = modifier,
            message = state.message.str()
        )

        is State.Failure -> FailureScreen(
            modifier = modifier,
            message = state.error.str(),
            retry = interactor::refresh
        )

        is State.Success -> {
            LazyColumn(modifier = modifier) {
                item {
                    val carouselState = rememberCarouselState { 3 }
                    FoldersContentCarousel(state = carouselState)
                    VerticalSpacer(24)
                }

                item {
                    FoldersList(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        items = state.value.folders,
                        onFolderSelected = { interactor.select(it) }
                    )
                    VerticalSpacer(8)
                }
            }
        }
    }
}