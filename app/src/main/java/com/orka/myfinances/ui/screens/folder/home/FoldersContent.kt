package com.orka.myfinances.ui.screens.folder.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.resources.models.folder.folders
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.lib.ui.components.spacer.FooterSpacer
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.contents.StateFulContent
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.buttons.OptionButton
import com.orka.myfinances.ui.resources.stockItemsState
import com.orka.myfinances.ui.models.content.FoldersContentModel
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentCarousel
import com.orka.myfinances.ui.screens.folder.home.parts.StockItemsRow
import com.orka.myfinances.ui.screens.folder.toUiModel
import com.orka.myfinances.ui.screens.stock.StockContentInteractor

@Composable
fun FoldersContent(
    modifier: Modifier = Modifier,
    state: State<FoldersContentModel>,
    interactor: FoldersContentInteractor,
    onAddProductClick: () -> Unit,
    pinnedCategoriesContent: LazyListScope.(List<Id>) -> Unit = {}
) {
    StateFulContent(
        modifier = modifier,
        state = state,
        onRetry = interactor::refresh
    ) { modifier, model ->
        PullToRefreshBox(
            modifier = modifier,
            isRefreshing = false,
            onRefresh = interactor::refresh
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    val carouselState = rememberCarouselState { 3 }
                    FoldersContentCarousel(state = carouselState)
                }

                item {
                    VerticalSpacer(16)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OptionButton(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.add_order),
                            painter = painterResource(R.drawable.add),
                            enabled = false,
                            onClick = {}//TODO
                        )

                        HorizontalSpacer(8)
                        OptionButton(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.add_product),
                            painter = painterResource(R.drawable.add),
                            onClick = onAddProductClick,
                            enabled = model.isDefaultCategorySet
                        )
                    }
                }

                item {
                    VerticalSpacer(24)
                    FoldersList(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        items = model.folders,
                        onFolderSelected = { interactor.select(it) }
                    )
                }

                if(model.pinnedCategories != null) {
                    pinnedCategoriesContent(model.pinnedCategories)
                }

                item {
                    VerticalSpacer(24)
                    OptionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        text = stringResource(R.string.go_to_pictures),
                        painter = painterResource(R.drawable.photo),
                        enabled = false,
                        onClick = {}//TODO
                    )
                }

                item { FooterSpacer() }
            }
        }
    }
}

@DefaultPreview
@Composable
private fun FoldersContentPreview() {
    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = "Home"
    ) { paddingValues ->
        FoldersContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = State.Success(
                value = FoldersContentModel(
                    folders = folders.map { it.toUiModel() },
                    isDefaultCategorySet = true,
                    pinnedCategories = listOf(id1, id2)
                )
            ),
            onAddProductClick = {},
            interactor = FoldersContentInteractor.dummy
        ) {
            item {
                VerticalSpacer(16)
                StockItemsRow(
                    title = "Pinned Category",
                    state = stockItemsState,
                    interactor = StockContentInteractor.dummy
                )
            }

            item {
                VerticalSpacer(16)
                StockItemsRow(
                    title = "Pinned Category",
                    state = stockItemsState,
                    interactor = StockContentInteractor.dummy
                )
            }
        }
    }
}