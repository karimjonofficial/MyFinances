package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.home.parts.FoldersList
import com.orka.myfinances.ui.screens.home.parts.FoldersContentCarousel
import com.orka.myfinances.ui.screens.home.viewmodel.FoldersState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FoldersContent(
    modifier: Modifier = Modifier,
    state: FoldersState,
    onNavigateToFolder: (Folder) -> Unit
) {
    when (state) {
        is FoldersState.Initial -> LoadingScreen(modifier)
        is FoldersState.Error -> FailureScreen(modifier = modifier, message = stringResource(R.string.error))
        is FoldersState.Loading -> LoadingScreen(modifier)

        is FoldersState.Success -> {
            val carouselState = rememberCarouselState { 3 }

            Column(modifier = modifier) {
                FoldersContentCarousel(state = carouselState)
                VerticalSpacer(24)
                FoldersList(
                    items = state.folders,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    onFolderSelected = onNavigateToFolder
                )
            }
        }
    }
}