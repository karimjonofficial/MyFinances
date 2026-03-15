package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.home.parts.FoldersContentCarousel
import com.orka.myfinances.ui.screens.home.parts.FoldersList
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersContentInteractor
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersState

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
            Column(modifier = modifier) {
                FoldersContentCarousel(folderCount = state.folders.size)

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