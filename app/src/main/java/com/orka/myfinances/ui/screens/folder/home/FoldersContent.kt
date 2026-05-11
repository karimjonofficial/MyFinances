package com.orka.myfinances.ui.screens.folder.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.folder.folders
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.FooterSpacer
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.contents.StateFulContent
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentCarousel
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel
import com.orka.myfinances.ui.screens.folder.toUiModel

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
                VerticalSpacer(8)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OptionButton(
                        modifier = Modifier.weight(1f),
                        text = "Add order",
                        painter = painterResource(R.drawable.add),
                        onClick = {}//TODO
                    )

                    HorizontalSpacer(8)
                    OptionButton(
                        modifier = Modifier.weight(1f),
                        text = "Add product",
                        painter = painterResource(R.drawable.add),
                        onClick = {}//TODO
                    )
                }
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

@Preview
@Composable
private fun FoldersContentPreview() {
    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = "Home"
    ) { paddingValues ->
        FoldersContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = State.Success(folders.map { it.toUiModel() }),
            interactor = FoldersContentInteractor.dummy
        )
    }
}